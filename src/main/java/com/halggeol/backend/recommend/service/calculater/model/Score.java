package com.halggeol.backend.recommend.service.calculater.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Score {
    private String id;
    private double yieldScore;
    private double riskScore;
    private double costScore;
    private double liquidityScore;
    private double complexityScore;

    public double getFinalScore(String productType) {
        return switch (productType) {
            case "deposit", "forex", "pension", "savings" -> weighted(0.3, 0.2, 0.05, 0.05);
            case "fund" -> weighted(0.2, 0.15, 0.1, 0.15);
            default -> avg(); // 기본 평균
        };
    }

    private double weighted(double wr, double wc, double wl, double wx) {
        return (0.4 * yieldScore + wr * riskScore + wc * costScore + wl * liquidityScore + wx * complexityScore);
    }

    private double avg() {
        return (yieldScore + riskScore + costScore + liquidityScore + complexityScore) / 5.0;
    }
}
