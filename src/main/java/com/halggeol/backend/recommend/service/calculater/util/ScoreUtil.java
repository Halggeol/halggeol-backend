package com.halggeol.backend.recommend.service.calculater.util;

import com.halggeol.backend.recommend.service.calculater.ScoreCalculator;

public class ScoreUtil {

    public static double normalizeRate(double rate) {
        return (rate - ScoreCalculator.MIN_RATE) / (ScoreCalculator.MAX_RATE - ScoreCalculator.MIN_RATE);
    }

    public static double normalizeFundPriceMovement(double priceMovement) {
        return (priceMovement - ScoreCalculator.MIN_FUND_PRICE_MOVEMENT) /
               (ScoreCalculator.MAX_FUND_PRICE_MOVEMENT - ScoreCalculator.MIN_FUND_PRICE_MOVEMENT);
    }

    public static double normalizeFundTER(double ter) {
        return (ter - ScoreCalculator.MIN_FUND_TER) / (
            ScoreCalculator.MAX_FUND_TER - ScoreCalculator.MIN_FUND_TER);
    }

    public static double fixedRisk(int riskLevel) {
        return (6 - riskLevel) / 5.0; // 위험 등급이 낮을수록 점수가 높음
    }

    public static double joinDenyScore(int joinDeny) {
        return switch (joinDeny) {
            case 1 -> 0.1; // 가입 가능
            case 2 -> 0.05; // 가입 제한
            case 3 -> 0.01; // 가입 불가
            case 4 -> 0.001; // 가입 불가, 예외적 조건
            default -> 0.0; // 기타 경우
        };
    }

}
