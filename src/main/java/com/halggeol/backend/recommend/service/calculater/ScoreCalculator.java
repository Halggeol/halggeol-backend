package com.halggeol.backend.recommend.service.calculater;

import com.halggeol.backend.recommend.dto.ProductVectorUpdateResponseDTO;
import com.halggeol.backend.recommend.service.calculater.model.Score;
import com.halggeol.backend.recommend.service.calculater.strategy.DepositScoreStrategy;
import com.halggeol.backend.recommend.service.calculater.strategy.ForexScoreStrategy;
import com.halggeol.backend.recommend.service.calculater.strategy.FundScoreStrategy;
import com.halggeol.backend.recommend.service.calculater.strategy.PensionScoreStrategy;
import com.halggeol.backend.recommend.service.calculater.strategy.SavingsScoreStrategy;
import com.halggeol.backend.recommend.service.calculater.strategy.ScoreStrategy;
import java.util.Map;

public class ScoreCalculator {

    public static double MAX_RATE;
    public static double MIN_RATE;
    public static double MAX_SAVE_TERM;
    public static double MIN_SAVE_TERM;
    public static double MAX_FUND_PRICE_MOVEMENT; // 최대 펀드 가격 변동률
    public static double MIN_FUND_PRICE_MOVEMENT; // 최소 펀드 가격 변동률
    public static double MAX_FUND_TER;
    public static double MIN_FUND_TER;

    private static final Map<String, ScoreStrategy<?>> strategyMap = Map.of(
        "deposit", new DepositScoreStrategy(),
        "savings", new SavingsScoreStrategy(),
        "fund", new FundScoreStrategy(),
        "pension", new PensionScoreStrategy(),
        "forex", new ForexScoreStrategy()
    );

    @SuppressWarnings("unchecked")
    public static <T> ProductVectorUpdateResponseDTO calculate(String productType, T dto) {
        ScoreStrategy<T> strategy = (ScoreStrategy<T>) strategyMap.get(productType);
        if (strategy == null) throw new IllegalArgumentException("Unsupported product type: " + productType);
        Score score = strategy.calculate(dto);
        return ProductVectorUpdateResponseDTO.builder()
                .yieldScore(score.getYieldScore())
                .riskScore(score.getRiskScore())
                .costScore(score.getCostScore())
                .liquidityScore(score.getLiquidityScore())
                .complexityScore(score.getComplexityScore())
                .algoCode(score.getFinalScore(productType))
                .build();
    }
}
