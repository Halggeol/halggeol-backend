package com.halggeol.backend.insight.util.calculator;

import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class RegretInsightCalculator {

    private static final double INVESTMENT_RATIO = 0.2;
    private static final double REGRET_SCORE_FACTOR = 500.0;
    private static final double MAX_SCORE = 100.0;
    private static final double MIN_SCORE = 0.0;

    public record RegretInsight(double regretScore, double missAmount) {
        public static final RegretInsight ZERO = new RegretInsight(0, 0);
    }

    public static RegretInsight calculate(List<ProfitSimulationDTO> profits) {
        if (profits == null || profits.isEmpty() || profits.get(0).getAsset() == null) {
            return RegretInsight.ZERO;
        }
        long curAsset = profits.get(0).getAsset();
        if (curAsset <= 0) return RegretInsight.ZERO;

        double missAmount = profits.stream()
            .mapToDouble(p -> p.getLostInvestment() != null ? p.getLostInvestment() : 0)
            .sum();

        return calculateScoreInternal(missAmount, curAsset);
    }

    public static RegretInsight calculateForex(List<ForexCompareDTO> input) {
        if (input == null || input.isEmpty() || input.get(0).getAsset() == null) {
            return RegretInsight.ZERO;
        }
        long curAsset = input.get(0).getAsset();
        if (curAsset <= 0) return RegretInsight.ZERO;

        double principal = curAsset * INVESTMENT_RATIO;
        BigDecimal diffPercent = input.get(0).getDiffPercent();

        double missAmount = diffPercent
            .multiply(BigDecimal.valueOf(principal))
            .setScale(0, RoundingMode.HALF_UP).intValue();

        return calculateScoreInternal(missAmount, curAsset);
    }

    private static RegretInsight calculateScoreInternal(double missAmount, long currentAsset) {
        if (missAmount <= 0) {
            return RegretInsight.ZERO;
        }

        // 후회지수 계산 공식: log(1 + (놓친 금액 / 현재 자산)) * 계수
        double rawRegretScore = Math.log1p(missAmount / currentAsset) * REGRET_SCORE_FACTOR;

        // 점수를 0~100 사이로 제한
        double regretScore = Math.min(Math.max(rawRegretScore, MIN_SCORE), MAX_SCORE);

        return new RegretInsight(regretScore, missAmount);
    }
}