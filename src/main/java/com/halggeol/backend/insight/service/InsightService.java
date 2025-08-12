package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.*;
import com.halggeol.backend.security.domain.CustomUser;

import java.util.List;

public interface InsightService {
    List<InsightDTO> getTop3MissedProducts(int round, CustomUser user);

//    List<InsightDTO> getFundInsight();
//
//    List<InsightDTO> getAggressivePensionInsight();

    // 새로 추가: 사용자별 펀드 조회
    List<InsightDTO> getFundInsightByUser(Long userId);

    // 새로 추가: 사용자별 공격형 연금 조회
    List<InsightDTO> getAggressivePensionInsightByUser(Long userId);

    List<ExchangeRateDTO> getExchangeRates(String searchDate);

    //스케줄러 수동
    void fetchAndSaveExchangeRates();

    // 처음 http://localhost:8080/api/insight 여기 상품 목록 가져오기
    List<InsightRoundDTO> getAllInsightRoundsByUser(Long userId);

    // 기존 메서드 시그니처 변경 - 타입 필터링 추가
    List<InsightRoundWithProductsDTO> getAllRoundsWithProductsByUser(Long userId, String type);
}
