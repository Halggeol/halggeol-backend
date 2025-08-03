package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.domain.ProductType;
import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import com.halggeol.backend.insight.dto.RegretSurveyRequestDTO;
import com.halggeol.backend.insight.mapper.InsightDetailMapper;
import com.halggeol.backend.insight.service.calculator.ProfitCalculator;
import com.halggeol.backend.insight.service.calculator.RegretInsightCalculator;
import com.halggeol.backend.security.domain.CustomUser;
import java.util.Collections;
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

    @Override
    @Transactional(readOnly = true)
    public InsightDetailResponseDTO getInsightDetail(Integer round, String productId, CustomUser user) {
        int userId = user.getUser().getId();
        InsightDetailResponseDTO insightResponse = fetchProductInfoByType(round, productId, userId);
        String recDate = insightResponse.getRecDate();
        String anlzDate = insightResponse.getAnlzDate();

        ProductType productType = ProductType.fromProductId(productId);
        List<ProfitSimulationDTO> simulation = fetchSimulationByType(productType, round, productId, userId, recDate, anlzDate);
        if (insightResponse == null) {
            log.warn("No insight detail found for productId={}, userId={}, recDate={}, anlzDate={}", productId, userId, recDate, anlzDate);
            throw new IllegalArgumentException("해당 상품에 대한 상세정보가 없습니다.");
        }
        insightResponse.setProfits(simulation);

        // 수익 시뮬레이션 계산
        ProfitCalculationInput input = ProfitCalculationInput.from(insightResponse);
        List<ProfitSimulationDTO> profits = ProfitCalculator.calculateProfitSimulations(input);
        insightResponse.setProfits(profits);

        // 후회지수 계산
        RegretInsightCalculator.RegretInsight regret = RegretInsightCalculator.calculate(insightResponse.getProfits());

        insightResponse.setRegretScore((int) regret.getRegretScore());
        insightResponse.setMissAmount((int) regret.getMissAmount());

        return insightResponse;
    }

    // 외환은 추후 합쳐야 함
    private InsightDetailResponseDTO fetchProductInfoByType(int round, String productId, int userId) {
        ProductType type = ProductType.fromProductId(productId);
        return switch (type) {
            case DEPOSIT -> mapper.getDepositInfo(round, productId, userId);
            case SAVINGS -> mapper.getSavingsInfo(round, productId, userId);
            case CONSERVATIVE -> mapper.getConservativePensionInfo(round, productId, userId);
            case AGGRESSIVE -> mapper.getAggressivePensionInfo(round, productId, userId);
            case FUND -> mapper.getFundInfo(round, productId, userId);
        };
    }
    private List<ProfitSimulationDTO> fetchSimulationByType(ProductType productType, int round, String productId, int userId, String recDate, String anlzDate) {
        return switch (productType) {
            case DEPOSIT -> mapper.getProfitSimulation(round, productId, userId, "deposit", recDate, anlzDate);
            case SAVINGS -> mapper.getProfitSimulation(round, productId, userId, "savings", recDate, anlzDate);
            case CONSERVATIVE -> mapper.getProfitSimulation(round, productId, userId, "conservative", recDate, anlzDate);
            case AGGRESSIVE -> mapper.getProfitSimulation(round, productId, userId, "aggressive", recDate, anlzDate);
            case FUND -> mapper.getProfitSimulation(round, productId, userId, "fund", recDate, anlzDate);
        };
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
