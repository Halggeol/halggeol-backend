package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.*;
import com.halggeol.backend.insight.mapper.InsightMapper;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightServiceImpl implements InsightService {

    private final InsightMapper insightMapper;

    // application.properties에 저장해둔 인증키
    @Value("${exchange.apiKey}")
    private String API_KEY ;

    // SSL 인증서 문제 해결을 위한 메서드
    private static void disableSSLCertificateChecking() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Map<String, Map<String, BigDecimal>> cachedRateMap = new ConcurrentHashMap<>();

    @Override
    public List<InsightDTO> getTop3MissedProducts(int round, CustomUser user) {
        int userId = user.getUser().getId();
        return insightMapper.getTop3MissedProducts(round, userId);
    }

    @Override
    public List<InsightDTO> getFundInsight() {
        return insightMapper.getFundInsight();
    }

    @Override
    public List<InsightDTO> getAggressivePensionInsight() {
        return insightMapper.getAggressivePensionInsight();
    }

    @Override
    public List<ExchangeRateDTO> getExchangeRates(String searchDate) {
        // 캐시에서 먼저 확인
        if (cachedRateMap.containsKey(searchDate)) {
            Map<String, BigDecimal> cachedRates = cachedRateMap.get(searchDate);
            return convertMapToList(cachedRates, searchDate);
        }

        // API 호출 시도 (재시도 로직 포함)
        List<ExchangeRateDTO> result = fetchExchangeRatesWithRetry(searchDate);

        // API 호출 실패 시 이전 날짜 데이터로 대체
        if (result.isEmpty()) {
            result = fetchFallbackData(searchDate);
        }

        return result;
    }

    /*** 재시도 로직을 포함한 환율 데이터 조회*/
    private List<ExchangeRateDTO> fetchExchangeRatesWithRetry(String searchDate) {
        int maxRetries = 3;
        int retryDelay = 2000; // 2초

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                List<ExchangeRateDTO> result = callExchangeRateApi(searchDate);
                if (!result.isEmpty()) {
                    return result;
                }
            } catch (Exception e) {
                System.err.println("API 호출 시도 " + attempt + " 실패: " + e.getMessage());

                if (attempt < maxRetries) {
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        System.err.println("모든 API 호출 시도 실패: " + searchDate);
        return new ArrayList<>();
    }

    /*** 실제 API 호출 메서드*/
    private List<ExchangeRateDTO> callExchangeRateApi(String searchDate) throws Exception {
        List<ExchangeRateDTO> list = new ArrayList<>();
        disableSSLCertificateChecking();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // 변경된 한국수출입은행 url 수정
            String apiUrl = "https://oapi.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="
                    + API_KEY + "&searchdate=" + searchDate + "&data=AP01";

            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();

            // 연결 설정
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // 10초로 단축
            connection.setReadTimeout(10000);    // 10초로 단축
            connection.setInstanceFollowRedirects(false);

            // 헤더 설정
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en;q=0.8");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Referer", "https://www.koreaexim.go.kr/");

            int responseCode = connection.getResponseCode();

            // 응답 코드 확인
            if (responseCode == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                String responseBody = sb.toString().trim();

                // 빈 응답 또는 에러 메시지 체크
                if (responseBody.isEmpty() || responseBody.startsWith("{\"error")) {
                    throw new Exception("API에서 빈 응답 또는 에러 반환: " + responseBody);
                }

                // JSON 파싱
                JSONArray jsonArr = (JSONArray) new JSONParser().parse(responseBody);
                Map<String, BigDecimal> rateMap = new HashMap<>();

                for (Object obj : jsonArr) {
                    JSONObject jsonObj = (JSONObject) obj;
                    String curUnit = (String) jsonObj.get("cur_unit");
                    String rateStr = (String) jsonObj.get("deal_bas_r");

                    if (curUnit != null && rateStr != null && !rateStr.isEmpty()) {
                        try {
                            BigDecimal rate = new BigDecimal(rateStr.replace(",", ""));
                            rateMap.put(curUnit.trim(), rate);

                            ExchangeRateDTO dto = new ExchangeRateDTO();
                            dto.setCurUnit(curUnit);
                            dto.setCurNm((String) jsonObj.get("cur_nm"));
                            dto.setDealBasR(rate);
                            dto.setBaseDate(searchDate);
                            list.add(dto);
                        } catch (NumberFormatException e) {
                            System.err.println("환율 파싱 오류: " + curUnit + " = " + rateStr);
                        }
                    }
                }

                // 캐시 저장
                if (!rateMap.isEmpty()) {
                    cachedRateMap.putIfAbsent(searchDate, rateMap);
                }

            } else if (responseCode == 302 || responseCode == 301) {
                throw new Exception("API 리디렉션 발생: " + responseCode);
            } else {
                throw new Exception("API 응답 오류: " + responseCode);
            }

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { /* 무시 */ }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return list;
    }

    /*** API 호출 실패 시 대체 데이터 조회 (이전 날짜들 시도)*/
    private List<ExchangeRateDTO> fetchFallbackData(String originalDate) {
        System.out.println("대체 데이터 조회 시작: " + originalDate);

        try {
            LocalDate date = LocalDate.parse(originalDate, DateTimeFormatter.ofPattern("yyyyMMdd"));

            // 최대 7일 전까지 시도
            for (int i = 1; i <= 7; i++) {
                LocalDate fallbackDate = date.minusDays(i);
                String fallbackDateStr = fallbackDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                // 주말 건너뛰기 (토요일=6, 일요일=7)
                if (fallbackDate.getDayOfWeek().getValue() >= 6) {
                    continue;
                }

                System.out.println("대체 날짜 시도: " + fallbackDateStr);

                // 캐시에서 먼저 확인
                if (cachedRateMap.containsKey(fallbackDateStr)) {
                    Map<String, BigDecimal> cachedRates = cachedRateMap.get(fallbackDateStr);
                    System.out.println("캐시에서 대체 데이터 발견: " + fallbackDateStr);
                    return convertMapToList(cachedRates, originalDate); // 원본 날짜로 반환
                }

                // API 호출 시도
                try {
                    List<ExchangeRateDTO> fallbackResult = callExchangeRateApi(fallbackDateStr);
                    if (!fallbackResult.isEmpty()) {
                        System.out.println("대체 데이터 API 호출 성공: " + fallbackDateStr);
                        // 원본 날짜로 변경해서 반환
                        return fallbackResult.stream()
                                .peek(dto -> dto.setBaseDate(originalDate))
                                .collect(Collectors.toList());
                    }
                } catch (Exception e) {
                    System.err.println("대체 데이터 API 호출 실패: " + fallbackDateStr + " - " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("대체 데이터 조회 중 오류: " + e.getMessage());
        }

        System.err.println("모든 대체 데이터 조회 실패");
        return new ArrayList<>();
    }

    /*** Map을 List로 변환하는 헬퍼 메서드*/
    private List<ExchangeRateDTO> convertMapToList(Map<String, BigDecimal> rateMap, String baseDate) {
        List<ExchangeRateDTO> result = new ArrayList<>();
        rateMap.forEach((cur, rate) -> {
            ExchangeRateDTO dto = new ExchangeRateDTO();
            dto.setCurUnit(cur);
            dto.setDealBasR(rate);
            dto.setBaseDate(baseDate);
            result.add(dto);
        });
        return result;
    }

    /*** 개선된 getTodayRatesMap - 대체 데이터 로직 포함*/
    private Map<String, BigDecimal> getTodayRatesMap(String date) {
        // 먼저 캐시에서 확인
        Map<String, BigDecimal> existingRates = cachedRateMap.get(date);
        if (existingRates != null && !existingRates.isEmpty()) {
            return existingRates;
        }

        // API에서 가져오기 (재시도 및 대체 로직 포함)
        List<ExchangeRateDTO> list = getExchangeRates(date);
        Map<String, BigDecimal> map = new HashMap<>();

        for (ExchangeRateDTO dto : list) {
            map.put(dto.getCurUnit(), dto.getDealBasR());
        }

        // 데이터가 있으면 캐시에 저장
        if (!map.isEmpty()) {
            cachedRateMap.putIfAbsent(date, map);
            return cachedRateMap.get(date);
        }

        // 여전히 데이터가 없으면 빈 맵 반환
        return new HashMap<>();
    }

    /*** 사용 가능한 환율 데이터 날짜 찾기 */
    private String findUsableExchangeRateDate() {
        LocalDate today = LocalDate.now();

        for (int i = 0; i <= 3; i++) {
            LocalDate targetDate = today.minusDays(i);

            // 주말 건너뛰기
            if (targetDate.getDayOfWeek().getValue() >= 6) {
                continue;
            }

            String dateStr = targetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            // 캐시에 있으면 바로 사용
            if (cachedRateMap.containsKey(dateStr) && !cachedRateMap.get(dateStr).isEmpty()) {
                System.out.println("캐시에서 사용 가능한 날짜 발견: " + dateStr);
                return dateStr;
            }

            // API 테스트 호출
            try {
                List<ExchangeRateDTO> testResult = fetchExchangeRatesWithRetry(dateStr);
                if (!testResult.isEmpty()) {
                    System.out.println("API에서 사용 가능한 날짜 발견: " + dateStr);
                    return dateStr;
                }
            } catch (Exception e) {
                System.err.println("날짜 테스트 실패: " + dateStr + " - " + e.getMessage());
            }
        }

        // 기본값으로 어제 반환
        return today.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private BigDecimal getLatestRateFromApi(String currency, String date) {
        List<ExchangeRateDTO> rates = getExchangeRates(date);
        for (ExchangeRateDTO dto : rates) {
            if (dto.getCurUnit().startsWith(currency)) {
                return dto.getDealBasR();
            }
        }
        return null;
    }

    //환율 OPEN API 스케줄러 코드
    @Transactional
    public void fetchAndSaveExchangeRates(String searchDate) {
        List<ExchangeRateDTO> rates = getExchangeRates(searchDate);
        if (rates.isEmpty()) {
            System.err.println("[환율 저장 실패] API 데이터 없음: " + searchDate);
            return;
        }
        for (ExchangeRateDTO dto : rates) {
            try {
                // curUnit에서 괄호와 그 안의 내용 제거 (예: "JPY(100)" -> "JPY")
                String cleanedCurUnit = dto.getCurUnit().replaceAll("\\(.*\\)", "").trim();

                // DB 존재 여부 확인할 때도 깨끗한 값 사용
                boolean exists = insightMapper.existsExchangeRate(cleanedCurUnit, searchDate);

                if (!exists) {
                    // DTO의 curUnit을 깨끗한 값으로 변경 후 저장
                    dto.setCurUnit(cleanedCurUnit);
                    insightMapper.insertExchangeRate(dto);
                }
            } catch (Exception e) {
                System.err.println("[환율 저장 오류] " + dto.getCurUnit() + ": " + e.getMessage());
            }
        }
        System.out.println("[환율 저장 완료] " + searchDate + " (" + rates.size() + " 건)");
    }

    @Override
    //오늘 날짜 기준으로 환율 데이터 조회하고 저장하는 기능
    public void fetchAndSaveExchangeRates() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        fetchAndSaveExchangeRates(today); // 기존 메서드 재활용
    }

////    수동으로 환율 open api 호출해서 forex_track에 저장
//    @Override
//    public void fetchAndSaveExchangeRates() {
////        현재 날짜 기준으로 몇일전
////        String targetDate = LocalDate.now().minusDays(4).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//

    /// /        날짜 직접 지정
//        String targetDate = "20250706";
//        fetchAndSaveExchangeRates(targetDate);
//    }


    // 처음 http://localhost:8080/api/insight 여기 상품 목록 가져오기
    @Override
    public List<InsightRoundDTO> getAllInsightRoundsByUser(Long userId) {
        return insightMapper.getAllInsightRoundsByUser(userId);
    }

    public List<InsightRoundWithProductsDTO> getAllRoundsWithProductsByUser(Long userId) {
        // 1) 라운드별 상품ID 목록 가져오기
        List<InsightRoundDTO> rounds = getAllInsightRoundsByUser(userId);

        // 2) 라운드별 상품 상세 리스트로 변환
        List<InsightRoundWithProductsDTO> result = new ArrayList<>();

        for (InsightRoundDTO roundDTO : rounds) {
            int round = roundDTO.getRound();
            List<InsightDTO> products = insightMapper.getAllProductsByRoundAndUser(round, userId);
            result.add(new InsightRoundWithProductsDTO(round, products));
        }
        return result;
    }

}

