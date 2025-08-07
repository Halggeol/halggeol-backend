package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.ExchangeRateDTO;
import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import com.halggeol.backend.recommend.service.RecommendServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InsightService {
    List<InsightDTO> getTop3MissedProducts(int month, int year);

    List<InsightDTO> getFundInsight();

    List<InsightDTO> getAggressivePensionInsight();

    List<ExchangeRateDTO> getExchangeRates(String searchDate);

//    List<ForexCompareDTO> compareForexRegretItems(Long userId);

    //기존 : 특정 날짜 기준 외환 비교
//    List<ForexCompareDTO> compareForexRegretItems(Long userId, LocalDate date);

    // 추가: userId만으로 모든 recId 회차의 ForexCompareDTO 리스트 반환
//    List<ForexCompareDTO> getUserForexCompareList(Long userId);

//    Map<Long, List<ForexCompareDTO>> getUserForexCompareGrouped(Long userId);

    //유사도 측정
//    List<RecommendServiceImpl.Recommendation> getSimilarProductsForInsight(String productId);
}
