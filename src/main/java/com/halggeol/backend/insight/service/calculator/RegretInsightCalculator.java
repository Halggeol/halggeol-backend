package com.halggeol.backend.insight.service.calculator;

import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class RegretInsightCalculator {
    @Getter
    @AllArgsConstructor
    public static class RegretInsight {
        private final double regretScore;
        private final double missAmount;
    }

    public static RegretInsight calculate(List<ProfitSimulationDTO> profits) {
        long curAsset = profits.get(0).getAsset();
        if (curAsset <= 0) return new RegretInsight(0, 0);

        double missAmount = profits.stream()
            .mapToDouble(p -> p.getLostInvestment() != null ? p.getLostInvestment() : 0)
            .sum();
        if (missAmount <= 0) return new RegretInsight(0, 0);

        double rawRegretScore = Math.log1p(missAmount / curAsset) * 150;
        System.out.println(rawRegretScore);
        double regretScore = Math.min(Math.max(rawRegretScore, 0), 100); // 범위 0~100 사이 제한

        return new RegretInsight(regretScore, missAmount);
    }
}
