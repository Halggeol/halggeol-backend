package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.ExchangeRateDTO;
import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import com.halggeol.backend.insight.dto.RegretItemDTO;
import com.halggeol.backend.insight.mapper.InsightMapper;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
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
    private static String API_KEY = "ATw64SDmn6zzCgPUzOxDkXqya2O8RMSm";

    // SSL 인증서 문제 해결을 위한 메서드
    private static void disableSSLCertificateChecking() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
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
    public List<InsightDTO> getTop3MissedProducts(int month, int year) {
        return insightMapper.getTop3MissedProducts(month, year);
    }

    @Override
    public List<InsightDTO> getFundInsight() {
        return insightMapper.getFundInsight();
    }

    @Override
    public List<InsightDTO> getAggressivePensionInsight() {
        return insightMapper.getAggressivePensionInsight();
    }

//    public List<ExchangeRateDTO> getExchangeRates(String searchDate) {
//        List<ExchangeRateDTO> list = new ArrayList<>();
//
//        // SSL 인증서 체크 비활성화
//        disableSSLCertificateChecking();
//
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
//
//        try {
//            String apiUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="
//                    + API_KEY + "&searchdate=" + searchDate + "&data=AP01";
//
//            System.out.println("API URL: " + apiUrl);
//
//            URL url = new URL(apiUrl);
//            connection = (HttpURLConnection) url.openConnection();
//
//            // ✅ 개선: 자동 리다이렉트 활성화
//            connection.setInstanceFollowRedirects(true);
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(15000);
//            connection.setReadTimeout(15000);
//
//            // 헤더 설정
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
//            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
//            connection.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en;q=0.8");
//            connection.setRequestProperty("Connection", "keep-alive");
//            connection.setRequestProperty("Referer", "https://www.koreaexim.go.kr/");
//
//            int responseCode = connection.getResponseCode();
//            System.out.println("Response Code: " + responseCode);
//
//            if (responseCode != 200) {
//                System.out.println("API 호출 실패. Response Code: " + responseCode);
//
//                // 에러 응답 내용 확인
//                InputStream errorStream = connection.getErrorStream();
//                if (errorStream != null) {
//                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
//                    StringBuilder errorContent = new StringBuilder();
//                    String errorLine;
//                    while ((errorLine = errorReader.readLine()) != null) {
//                        errorContent.append(errorLine);
//                    }
//                    System.out.println("Error Response: " + errorContent.toString());
//                    errorReader.close();
//                }
//                return list;
//            }
//
//            // 성공적인 응답 읽기
//            InputStream inputStream = connection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//
//            String response = sb.toString();
//            System.out.println("API Response: " + response);
//
//            // 응답이 비어있는지 확인
//            if (response.trim().isEmpty()) {
//                System.out.println("API 응답이 비어있습니다.");
//                return list;
//            }
//
//            // JSON 파싱
//            JSONParser parser = new JSONParser();
//            Object parsedObj = parser.parse(response);
//
//            if (!(parsedObj instanceof JSONArray)) {
//                System.out.println("예상과 다른 JSON 형식: " + response);
//                return list;
//            }
//
//            JSONArray jsonArr = (JSONArray) parsedObj;
//
//            for (Object obj : jsonArr) {
//                JSONObject o = (JSONObject) obj;
//                ExchangeRateDTO dto = new ExchangeRateDTO();
//
//                dto.setCurUnit((String) o.get("cur_unit"));
//                dto.setCurNm((String) o.get("cur_nm"));
//
//                Object dealBasRObj = o.get("deal_bas_r");
//                if (dealBasRObj != null) {
//                    String dealBasRStr = dealBasRObj.toString().replace(",", "");
//                    if (!dealBasRStr.isEmpty() && !dealBasRStr.equals("null")) {
//                        dto.setDealBasR(new BigDecimal(dealBasRStr));
//                    }
//                }
//
//                dto.setBaseDate(searchDate);
//                list.add(dto);
//            }
//
//        } catch (Exception e) {
//            System.err.println("환율 API 호출 중 오류 발생:");
//            e.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) reader.close();
//            } catch (IOException ignored) {}
//            if (connection != null) connection.disconnect();
//        }
//
//        return list;
//    }

    @Override
    public List<ExchangeRateDTO> getExchangeRates(String searchDate) {
        if (cachedRateMap.containsKey(searchDate)) {
            Map<String, BigDecimal> cachedRates = cachedRateMap.get(searchDate);
            List<ExchangeRateDTO> result = new ArrayList<>();
            cachedRates.forEach((cur, rate) -> {
                ExchangeRateDTO dto = new ExchangeRateDTO();
                dto.setCurUnit(cur);
                dto.setDealBasR(rate);
                dto.setBaseDate(searchDate);
                result.add(dto);
            });
            return result;
        }

        List<ExchangeRateDTO> list = new ArrayList<>();
        disableSSLCertificateChecking();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            String apiUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="
                    + API_KEY + "&searchdate=" + searchDate + "&data=AP01";
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);

            // 🔽 여기를 수정
            connection.setInstanceFollowRedirects(false); // ✅ 리디렉션 무제한 방지
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Referer", "https://www.koreaexim.go.kr/");

            if (connection.getResponseCode() != 200) return list;

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);

            JSONArray jsonArr = (JSONArray) new JSONParser().parse(sb.toString());
            Map<String, BigDecimal> rateMap = new HashMap<>();

            for (Object obj : jsonArr) {
                JSONObject o = (JSONObject) obj;
                String curUnit = (String) o.get("cur_unit");
                String rateStr = (String) o.get("deal_bas_r");

                if (curUnit != null && rateStr != null && !rateStr.isEmpty()) {
                    BigDecimal rate = new BigDecimal(rateStr.replace(",", ""));
                    rateMap.put(curUnit.trim(), rate);

                    ExchangeRateDTO dto = new ExchangeRateDTO();
                    dto.setCurUnit(curUnit);
                    dto.setCurNm((String) o.get("cur_nm"));
                    dto.setDealBasR(rate);
                    dto.setBaseDate(searchDate);
                    list.add(dto);
                }
            }
            cachedRateMap.put(searchDate, rateMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ForexCompareDTO> compareForexRegretItems(Long userId) {
        List<RegretItemDTO> regretItems = insightMapper.getForexRegretItems(userId);

        List<ForexCompareDTO> result = new ArrayList<>();

        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Map<String, BigDecimal> todayRates = getTodayRatesMap(today);

        for (RegretItemDTO item : regretItems) {
            String productId = item.getProductId();
            String productName = insightMapper.getForexProductNameById(productId); // ✅ 상품명 조회
            LocalDate recDate = item.getRecDate();
            String currencyStr = item.getCurrency();

            if (currencyStr == null || currencyStr.isEmpty()) continue;

            String[] currencies = currencyStr.split(",\\s*");

            for (String currency : currencies) {
                currency = currency.trim();
                BigDecimal todayRate = todayRates.get(currency);

                if (todayRate == null) {
                    todayRate = getLatestRateFromApi(currency, today);
                }

                if (todayRate == null) continue;

                BigDecimal pastRate = insightMapper.getForexRateOnDate(productId, recDate, currency);
                if (pastRate == null) {
                    pastRate = insightMapper.getLatestForexRateBeforeDate(productId, currency, recDate);
                }

                if (pastRate == null) continue;

                BigDecimal diff = todayRate.subtract(pastRate);
                BigDecimal diffPercent = diff
                        .divide(pastRate, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));

                ForexCompareDTO dto = new ForexCompareDTO();
                dto.setRound(item.getRound()); // ✅ 이 한 줄 추가
                dto.setProductName(productName);
                dto.setCurUnit(currency);
                dto.setPastRate(pastRate);
                dto.setCurrentRate(todayRate);
                dto.setRecDate(recDate.toString());
                dto.setDiff(diff);
                dto.setDiffPercent(diffPercent);

                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<ForexCompareDTO> compareForexRegretItems(Long userId, LocalDate date) {
        List<RegretItemDTO> regretItems = insightMapper.getForexRegretItemsByDate(userId, date);

        List<ForexCompareDTO> result = new ArrayList<>();

        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Map<String, BigDecimal> todayRates = getTodayRatesMap(today);

        for (RegretItemDTO item : regretItems) {
            String productId = item.getProductId();
            String productName = insightMapper.getForexProductNameById(productId); // ✅ 상품명 조회
            LocalDate recDate = item.getRecDate();
            String currencyStr = item.getCurrency();

            if (currencyStr == null || currencyStr.isEmpty()) continue;

            String[] currencies = currencyStr.split(",\\s*");

            for (String currency : currencies) {
                currency = currency.trim();
                BigDecimal todayRate = todayRates.get(currency);

                if (todayRate == null) {
                    todayRate = getLatestRateFromApi(currency, today);
                }

                if (todayRate == null) continue;

                BigDecimal pastRate = insightMapper.getForexRateOnDate(productId, recDate, currency);
                if (pastRate == null) {
                    pastRate = insightMapper.getLatestForexRateBeforeDate(productId, currency, recDate);
                }

                if (pastRate == null) continue;

                BigDecimal diff = todayRate.subtract(pastRate);
                BigDecimal diffPercent = diff
                        .divide(pastRate, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));

                ForexCompareDTO dto = new ForexCompareDTO();
                dto.setRound(item.getRound()); // ✅ 이 한 줄 추가
                dto.setProductName(productName); // ✅ 세팅
                dto.setCurUnit(currency);
                dto.setPastRate(pastRate);
                dto.setCurrentRate(todayRate);
                dto.setRecDate(recDate.toString());
                dto.setDiff(diff);
                dto.setDiffPercent(diffPercent);

                result.add(dto);
            }
        }
        return result;
    }


//    // ✅ 새로 추가된 메서드: 오늘 기준으로 가장 가까운 환율을 Open API로 조회
//    private BigDecimal getLatestRateFromApi(String currency, String startDate) {
//        LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
//        int maxRetryDays = 1;
//
//        for (int i = 0; i < maxRetryDays; i++) {
//            String queryDate = date.minusDays(i).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//            List<ExchangeRateDTO> rates = getExchangeRates(queryDate);
//
//            for (ExchangeRateDTO dto : rates) {
//                if (dto.getCurUnit().startsWith(currency)) {
//                    System.out.println("[OpenAPI 대체 사용] " + currency + " @ " + queryDate + " => " + dto.getDealBasR());
//                    return dto.getDealBasR();
//                }
//            }
//        }
//
//        System.out.println("최근 " + maxRetryDays + "일 내 " + currency + " 환율을 찾지 못했습니다.");
//        return null;
//    }

    private BigDecimal getLatestRateFromApi(String currency, String date) {
        List<ExchangeRateDTO> rates = getExchangeRates(date);
        for (ExchangeRateDTO dto : rates) {
            if (dto.getCurUnit().startsWith(currency)) {
                return dto.getDealBasR();
            }
        }
        return null;
    }

    private Map<String, BigDecimal> getTodayRatesMap(String date) {
        return cachedRateMap.computeIfAbsent(date, d -> {
            List<ExchangeRateDTO> list = getExchangeRates(d);
            Map<String, BigDecimal> map = new HashMap<>();
            for (ExchangeRateDTO dto : list) {
                map.put(dto.getCurUnit(), dto.getDealBasR());
            }
            return map;
        });
    }

//    private Map<String, BigDecimal> getTodayRatesMap(String searchDate) {
//        Map<String, BigDecimal> rateMap = new HashMap<>();
//
//        // SSL 인증서 체크 비활성화
//        disableSSLCertificateChecking();
//
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
//
//        try {
//            String apiUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="
//                    + API_KEY + "&searchdate=" + searchDate + "&data=AP01";
//
//            URL url = new URL(apiUrl);
//            connection = (HttpURLConnection) url.openConnection();
//
//            // 리다이렉트 문제 해결을 위한 설정
//            connection.setInstanceFollowRedirects(false);
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(15000);
//            connection.setReadTimeout(15000);
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setRequestProperty("Referer", "https://www.koreaexim.go.kr/");
//
//            int responseCode = connection.getResponseCode();
//
//            // 리다이렉트 처리
//            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM ||
//                    responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||
//                    responseCode == HttpURLConnection.HTTP_SEE_OTHER ||
//                    responseCode == 307 || responseCode == 308) {
//
//                String newUrl = connection.getHeaderField("Location");
//                if (newUrl != null) {
//                    // 상대경로인 경우 절대경로로 변환
//                    if (newUrl.startsWith("/")) {
//                        newUrl = "https://www.koreaexim.go.kr" + newUrl;
//                    }
//
//                    connection.disconnect();
//
//                    URL redirectUrl = new URL(newUrl);
//                    connection = (HttpURLConnection) redirectUrl.openConnection();
//                    connection.setInstanceFollowRedirects(false);
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(15000);
//                    connection.setReadTimeout(15000);
//                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
//                    connection.setRequestProperty("Accept", "application/json");
//
//                    responseCode = connection.getResponseCode();
//                }
//            }
//
//            if (responseCode != 200) {
//                System.out.println("getTodayRatesMap API 호출 실패. Response Code: " + responseCode);
//                return rateMap;
//            }
//
//            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//            StringBuilder responseContent = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                responseContent.append(line);
//            }
//
//            String response = responseContent.toString();
//
//            if (response.trim().isEmpty()) {
//                return rateMap;
//            }
//
//            // JSON 파싱
//            JSONParser parser = new JSONParser();
//            JSONArray jsonArr = (JSONArray) parser.parse(response);
//
//            for (Object obj : jsonArr) {
//                JSONObject o = (JSONObject) obj;
//                String curUnit = (String) o.get("cur_unit");
//                String rateStr = (String) o.get("deal_bas_r");
//
//                if (curUnit != null && rateStr != null && !rateStr.isEmpty()) {
//                    BigDecimal rate = new BigDecimal(rateStr.replace(",", ""));
//                    rateMap.put(curUnit.trim(), rate);
//                }
//            }
//
//        } catch (Exception e) {
//            System.err.println("getTodayRatesMap 오류:");
//            e.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) reader.close();
//            } catch (IOException ignored) {}
//            if (connection != null) connection.disconnect();
//
//        }
//
//        return rateMap;
//    }

    @Override
    public Map<Long, List<ForexCompareDTO>> getUserForexCompareGrouped(Long userId) {
        List<ForexCompareDTO> list = getUserForexCompareList(userId);
        return list.stream()
                .collect(Collectors.groupingBy(dto -> Long.valueOf(dto.getRound()), LinkedHashMap::new, Collectors.toList()));
    }

    @Override
    public List<ForexCompareDTO> getUserForexCompareList(Long userId) {
        // 기존 compareForexRegretItems(userId) 메서드의 내용을 재사용
        return compareForexRegretItems(userId);
    }
}

