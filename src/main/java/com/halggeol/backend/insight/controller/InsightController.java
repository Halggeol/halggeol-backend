package com.halggeol.backend.insight.controller;

import com.halggeol.backend.insight.dto.*;
import com.halggeol.backend.insight.service.InsightDetailService;
import com.halggeol.backend.insight.service.InsightService;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/insight")
@RequiredArgsConstructor
public class InsightController {

    private final InsightService insightService;
    private final InsightDetailService insightDetailService;

    @GetMapping("/with-products")
    public List<InsightDTO> getInsightList(
        @AuthenticationPrincipal CustomUser user,
        @RequestParam(required = false) Integer round,
        @RequestParam(required = false) String type
    ) {
        if (user == null) {
            throw new IllegalArgumentException("인증된 사용자가 필요합니다.");
        }

        Long userId = (long) user.getUser().getId();

        if (round != null) {
            // 회차 기준으로 놓친 수익 Top 3 상품 조회
            return insightService.getTop3MissedProducts(round, user);
        } else if ("fund".equals(type)) {
            // 펀드 모아보기 - 사용자별로 필터링된 펀드만
            return insightService.getFundInsightByUser(userId);
        } else if ("aggressive_pension".equals(type)) {
            // 공격형 연금 모아보기 - 사용자별로 필터링된 공격형 연금만
            return insightService.getAggressivePensionInsightByUser(userId);
        } else {
            throw new IllegalArgumentException("잘못된 요청입니다. 쿼리 파라미터를 확인하세요.");
        }
    }

    //처음 http://localhost:8080/api/insight 여기 상품 목록 가져오기
    @GetMapping
    public ResponseEntity<List<InsightRoundWithProductsDTO>> getInsightRoundsWithProducts(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam(required = false) String type
    ) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = (long) user.getUser().getId();
        List<InsightRoundWithProductsDTO> data = insightService.getAllRoundsWithProductsByUser(userId, type);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{round}/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getInsightDetail(
        @PathVariable Integer round,
        @PathVariable String productId,
        @AuthenticationPrincipal CustomUser user) {
        InsightDetailResponseDTO response = insightDetailService.getInsightDetail(round, productId, user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/feedback")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateRegretSurvey(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody RegretSurveyRequestDTO request) {
        insightDetailService.updateRegretSurvey(user, request);
        return ResponseEntity.ok().build();
    }

    // api/insight/exchange-rate?date=2025-07-29 (특정 날짜 조회) , /api/insight/exchange-rate (date 파라미터 없으면 오늘 날짜 기준)
    @GetMapping("/exchange-rate")
    public ResponseEntity<Map<String, Object>> getExchangeRates(
            @RequestParam(required = false) String date) {

        Map<String, Object> response = new HashMap<>();

        try {
            // date 파라미터가 없으면 사용 가능한 최신 날짜로 조회
            if (date == null) {
                date = findUsableExchangeRateDate();
            }

            List<ExchangeRateDTO> rates = insightService.getExchangeRates(date);

            if (rates.isEmpty()) {
                response.put("success", false);
                response.put("message", "환율 데이터를 조회할 수 없습니다. 잠시 후 다시 시도해주세요.");
                response.put("data", new ArrayList<>());
                response.put("actualDate", date);
                return ResponseEntity.ok(response);
            }

            response.put("success", true);
            response.put("message", "환율 데이터 조회 성공");
            response.put("data", rates);
            response.put("requestedDate", date);
            response.put("actualDate", rates.get(0).getBaseDate());
            response.put("dataCount", rates.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "환율 데이터 조회 중 오류가 발생했습니다: " + e.getMessage());
            response.put("data", new ArrayList<>());
            return ResponseEntity.status(500).body(response);
        }
    }

    private String findUsableExchangeRateDate() {
        LocalDate today = LocalDate.now();

        // 오늘부터 최대 7일 전까지 확인
        for (int i = 0; i <= 7; i++) {
            LocalDate targetDate = today.minusDays(i);

            // 주말 건너뛰기 (토요일=6, 일요일=7)
            if (targetDate.getDayOfWeek().getValue() >= 6) {
                continue;
            }

            String dateStr = targetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            // 간단한 테스트 호출
            try {
                List<ExchangeRateDTO> testResult = insightService.getExchangeRates(dateStr);
                if (!testResult.isEmpty()) {
                    System.out.println("사용 가능한 환율 날짜 발견: " + dateStr);
                    return dateStr;
                }
            } catch (Exception e) {
                // 다음 날짜 시도
                continue;
            }
        }

        // 기본값으로 어제 반환 (평일 기준)
        LocalDate yesterday = today.minusDays(1);
        while (yesterday.getDayOfWeek().getValue() >= 6) {
            yesterday = yesterday.minusDays(1);
        }

        return yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    //스케줄(자동)을 수동으로 open api 연결 해보기
    @GetMapping("/fetch-exchange")
    public ResponseEntity<String> fetchExchangeManually() {
        insightService.fetchAndSaveExchangeRates();
        return ResponseEntity.ok("환율 데이터 수동 저장 완료");
    }
}
