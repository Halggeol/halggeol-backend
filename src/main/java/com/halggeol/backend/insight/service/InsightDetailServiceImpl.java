package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.domain.ProductType;
import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import com.halggeol.backend.insight.dto.RegretSurveyRequestDTO;
import com.halggeol.backend.insight.mapper.InsightDetailMapper;
import com.halggeol.backend.insight.util.calculator.ProfitCalculator;
import com.halggeol.backend.insight.util.calculator.RegretInsightCalculator;
import com.halggeol.backend.insight.service.strategy.InsightDetailFactory;
import com.halggeol.backend.insight.service.strategy.InsightDetailStrategy;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.recommend.service.RecommendServiceImpl.Recommendation;
import com.halggeol.backend.security.domain.CustomUser;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsightDetailServiceImpl implements InsightDetailService {

    private final InsightDetailMapper mapper;
    private final RecommendService recommendService;
    private final InsightDetailFactory factory;

    @Override
    @Transactional(readOnly = true)
    public InsightDetailResponseDTO getInsightDetail(Integer round, String productId, CustomUser user) {
        int userId = user.getUser().getId();
        ProductType productType = ProductType.fromProductId(productId);
        InsightDetailStrategy strategy = factory.getStrategy(productType);

        InsightDetailResponseDTO insightResponse = strategy.fetchProductInfo(round, productId, userId);

        LocalDate recDate = insightResponse.getRecDate();
        LocalDate anlzDate = insightResponse.getAnlzDate();

        // 외환 인사이트
        if(productType == ProductType.FOREX) {
            List<ForexCompareDTO> forexInfo = getForexCompare(userId, insightResponse.getCurrency(), recDate, anlzDate);
            insightResponse.setForexInfo(forexInfo);

            RegretInsightCalculator.RegretInsight regret = RegretInsightCalculator.calculateForex(forexInfo);

            insightResponse.setRegretScore((int) regret.getRegretScore());
            insightResponse.setMissAmount((int) regret.getMissAmount());

            return insightResponse;
        }

        // 외환 제외 인사이트
        List<ProfitSimulationDTO> simulation = strategy.fetchSimulation(round, productId, userId, recDate, anlzDate);
        insightResponse.setProfits(simulation);

        // 수익 시뮬레이션 계산
        ProfitCalculationInput input = ProfitCalculationInput.from(insightResponse);
        List<ProfitSimulationDTO> profits = ProfitCalculator.calculateProfitSimulations(input);
        insightResponse.setProfits(profits);

        // 후회지수 계산
        RegretInsightCalculator.RegretInsight regret = RegretInsightCalculator.calculate(insightResponse.getProfits());

        insightResponse.setRegretScore((int) regret.getRegretScore());
        insightResponse.setMissAmount((int) regret.getMissAmount());

        // 유사도 측정
        List<Recommendation> similarProducts = recommendService.getSimilarProducts(productId);
        insightResponse.setSimilarProducts(similarProducts);

        return insightResponse;
    }

    private List<ForexCompareDTO> getForexCompare(int userId, String currencies, LocalDate recDate, LocalDate anlzDate) {
        List<ForexCompareDTO> forexInfo = new ArrayList<>();
        String[] currencyArray = currencies.split(",\\s*");
        List<String> currencyList = Arrays.asList(currencyArray);

        Long asset = mapper.getAsset(userId, recDate);

        for(String currency : currencyList) {
            BigDecimal pastRate = mapper.getExchangeRate(currency, recDate);
            BigDecimal currRate = mapper.getExchangeRate(currency, anlzDate);

            BigDecimal diff = currRate.subtract(pastRate);
            BigDecimal diffPercent = diff
                .divide(pastRate, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

            ForexCompareDTO dto = new ForexCompareDTO();
            dto.setAsset(asset);
            dto.setCurUnit(currency);
            dto.setRecDate(recDate);
            dto.setPastRate(pastRate);
            dto.setAnlzDate(anlzDate);
            dto.setCurrentRate(currRate);
            dto.setDiff(diff);
            dto.setDiffPercent(diffPercent);

            forexInfo.add(dto);
        }
        return forexInfo;
    }

    @Override
    public void updateRegretSurvey(CustomUser user, RegretSurveyRequestDTO surveyRequest) {
        int userId = user.getUser().getId();
        String surveyResult = resolveSurveyResult(surveyRequest.getIsRegretted(), surveyRequest.getRegrettedReason());

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("productId", surveyRequest.getProductId());
        params.put("surveyResult", surveyResult);

        mapper.updateRegretSurvey(params);
    }

    private String resolveSurveyResult(Boolean isRegretted, Integer regrettedReason) {
        if (!Boolean.TRUE.equals(isRegretted)) {
            return "후회하지 않음";
        }
        return switch (regrettedReason) {
            case 1 -> "관심이 없어서";
            case 2 -> "상품 설명이 부족하거나 복잡해서";
            case 3 -> "상품을 신뢰할 수 없어서";
            case 4 -> "가입 절차가 번거로워서";
            default -> throw new IllegalArgumentException("유효하지 않은 후회 사유");
        };
    }
}
