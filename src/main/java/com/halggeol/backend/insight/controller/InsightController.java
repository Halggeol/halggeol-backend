package com.halggeol.backend.insight.controller;

import com.halggeol.backend.insight.dto.ExchangeRateDTO;
import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import com.halggeol.backend.insight.service.InsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insight")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Vue 개발 서버 허용
public class InsightController {

    private final InsightService insightService;

    @GetMapping
    public List<InsightDTO> getInsightList(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String type
    ) {
        if (month != null && year != null) {
            // 월/연도 기준으로 놓친 수익 Top 3 상품 조회
            return insightService.getTop3MissedProducts(month, year);
        } else if ("fund".equals(type)) {
            // 펀드 상품 전체 조회 (회고 상태 기준)
            return insightService.getFundInsight();
        } else if ("aggressive_pension".equals(type)) {
            // 공격형 연금 상품 전체 조회 (회고 상태 + 연금 ID A로 시작)
            return insightService.getAggressivePensionInsight();
        } else {
            throw new IllegalArgumentException("잘못된 요청입니다. 쿼리 파라미터를 확인하세요.");
        }
    }

    // api/insight/exchange-rate?date=2025-07-29 (특정 날짜 조회) , /api/insight/exchange-rate (date 파라미터 없으면 오늘 날짜 기준)
    @GetMapping("/exchange-rate")
    public List<ExchangeRateDTO> getExchangeRates(
            @RequestParam(required = false) String date) {

        // date 파라미터가 없으면 오늘 날짜로 조회
        if (date == null) {
            date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        }
        return insightService.getExchangeRates(date);
    }

    @GetMapping("/compare-forex")
    public List<ForexCompareDTO> compareForexByUser(
            @RequestParam Long userId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        if (date != null) {
            return insightService.compareForexRegretItems(userId, date);
        } else {
            return insightService.getUserForexCompareList(userId);
        }
    }

    @GetMapping("/compare-forex/grouped")
    public Map<Long, List<ForexCompareDTO>> compareForexGrouped(
            @RequestParam Long userId) {
        return insightService.getUserForexCompareGrouped(userId);
    }

}
