package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.ExchangeRateDTO;
import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import com.halggeol.backend.recommend.service.RecommendServiceImpl;
import com.halggeol.backend.security.domain.CustomUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InsightService {
    List<InsightDTO> getTop3MissedProducts(int round, CustomUser user);

    List<InsightDTO> getFundInsight();

    List<InsightDTO> getAggressivePensionInsight();

    List<ExchangeRateDTO> getExchangeRates(String searchDate);

    //스케줄러 수동
    void fetchAndSaveExchangeRates();

}
