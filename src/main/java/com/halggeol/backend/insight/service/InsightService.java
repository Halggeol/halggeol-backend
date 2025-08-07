package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.*;
import com.halggeol.backend.security.domain.CustomUser;

import java.util.List;

public interface InsightService {
    List<InsightDTO> getTop3MissedProducts(int round, CustomUser user);

    List<InsightDTO> getFundInsight();

    List<InsightDTO> getAggressivePensionInsight();

    List<ExchangeRateDTO> getExchangeRates(String searchDate);

    //스케줄러 수동
    void fetchAndSaveExchangeRates();

    // 처음 http://localhost:8080/api/insight 여기 상품 목록 가져오기
    List<InsightRoundDTO> getAllInsightRoundsByUser(Long userId);

    List<InsightRoundWithProductsDTO> getAllRoundsWithProductsByUser(Long userId);

}
