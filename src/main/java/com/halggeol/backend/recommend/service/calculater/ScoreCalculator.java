package com.halggeol.backend.recommend.service.calculater;

import com.halggeol.backend.recommend.dto.DepositAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ForexAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.FundAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.PensionAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.SavingsAlgorithmResponseDTO;
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

    private static double getJoinDenyScore(int joinDeny) {
        return switch (joinDeny) {
            case 1 -> 0.1; // 가입 가능
            case 2 -> 0.05; // 가입 제한
            case 3 -> 0.01; // 가입 불가
            case 4 -> 0.001; // 가입 불가, 예외적 조건
            default -> 0.0; // 기타 경우
        };
    }

    private static double getProfitScore(double rate) {
        return (rate - MIN_RATE) / (MAX_RATE - MIN_RATE);
    }

    private static double getRiskScore(int riskLevel) {
        return (6 - riskLevel) / 5.0; // 위험 등급이 낮을수록 점수가 높음
    }

    private static double getFinalScore(double yieldScore, double costScore, double riskScore, double liquidityScore, double complexityScore, String productType) {
        // 상품 유형에 따라 가중치를 다르게 적용
        // 기본 평균
        final double v1 =
            (0.4 * yieldScore + 0.3 * riskScore + 0.2 * liquidityScore + 0.05 * costScore
                    + 0.05 * complexityScore);
        final double v2 =
            0.4 * yieldScore + 0.2 * riskScore + 0.2 * liquidityScore + 0.1 * costScore
                + 0.1 * complexityScore;
        return switch (productType) {
            case "deposit" ->
                (0.4 * yieldScore + 0.2 * riskScore + 0.2 * liquidityScore + 0.1 * costScore
                    + 0.1 * complexityScore);
            case "savings" ->
                (0.4 * yieldScore + 0.3 * riskScore + 0.2 * liquidityScore + 0.05 * costScore
                    + 0.05 * complexityScore);
            case "fund" ->
                (0.4 * yieldScore + 0.2 * riskScore + 0.15 * liquidityScore + 0.1 * costScore
                    + 0.15 * complexityScore);
            case "pension" ->
                (0.4 * yieldScore + 0.3 * riskScore + 0.2 * liquidityScore + 0.05 * costScore
                    + 0.05 * complexityScore);
            case "forex" ->
                (0.4 * yieldScore + 0.2 * riskScore + 0.2 * liquidityScore + 0.1 * costScore
                    + 0.1 * complexityScore);
            default -> (yieldScore + riskScore + liquidityScore + costScore + complexityScore)
                / 5; // 기본 평균
        };
    }


    public static Map<String, Double> calculateDepositScore(DepositAlgorithmResponseDTO deposit) {
        double yieldScore = getProfitScore(deposit.getRate());
        double riskScore = getRiskScore(6); // 위험성 점수는 1.0로 고정
        double costScore = 0.0;
        double liquidityScore = ((deposit.getMinSaveTerm() - MIN_SAVE_TERM) / (MAX_SAVE_TERM - MIN_SAVE_TERM)) * 0.9 + (deposit.getExtraDeposit() ? 0.1 : 0.0); // 유동성 점수 계산
        double complexityScore = 0 + getJoinDenyScore(deposit.getJoinDeny());
        double finalScore = getFinalScore(yieldScore, costScore, riskScore, liquidityScore, complexityScore, "deposit");
        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
            "liquidityScore", liquidityScore, "complexityScore", complexityScore);
    }

    public static Map<String, Double> calculateSavingsScore(SavingsAlgorithmResponseDTO savings){
        double yieldScore = getProfitScore(savings.getRate());
        double riskScore = getRiskScore(6);
        double costScore = 0.0; // 비용 점수는 현재 0으로 설정
        double liquidityScore = ((savings.getMinSaveTerm() - MIN_SAVE_TERM) / (MAX_SAVE_TERM - MIN_SAVE_TERM)) * 0.9 + (savings.getRateType().contains("단리") ? 0.1 : 0.0); // 유동성 점수 계산
        double complexityScore = 0 + getJoinDenyScore(savings.getJoinDeny()) + (savings.getRateType().contains("단리") ? 0.1 : 0.0); // 단리 여부에 따라 복잡성 점수 추가
        double finalScore = getFinalScore(yieldScore, costScore, riskScore, liquidityScore, complexityScore, "savings");
        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
            "liquidityScore", liquidityScore, "complexityScore", complexityScore);
    }

    public static Map<String, Double> calculateFundScore(FundAlgorithmResponseDTO fund){
        if(fund == null) {
            return null;
        }
        double rate = fund.getRate();
        double yieldScore = (rate - MAX_RATE) / (MAX_RATE - MIN_RATE);
        double priceMovementScore = (fund.getFundPriceMovement() - MIN_FUND_PRICE_MOVEMENT) / (MAX_FUND_PRICE_MOVEMENT - MIN_FUND_PRICE_MOVEMENT);
        double riskScore = getRiskScore(fund.getRisk()) + priceMovementScore * 0.2; // 펀드 가격 변동률에 따른 위험성 점수 추가
        double costScore = (fund.getTER() - MIN_FUND_TER) / (MAX_FUND_TER - MIN_FUND_TER);
        double liquidityScore = 1; // 펀드의 유동성 점수는 1로 고정
        // 복잡성 점수는 펀드의 종류에 따라 다르게 설정
        double complexityScore;
        if(fund.getCategory().contains("주식형")) {
            complexityScore = 0.8; // 주식형 펀드는 복잡성이 높음
        } else if(fund.getCategory().contains("채권형")) {
            complexityScore = 0.6; // 채권형 펀드는 중간 정도의 복잡성
        } else if(fund.getCategory().contains("혼합형")) {
            complexityScore = 0.4; // 혼합형 펀드는 상대적으로 단순함
        } else {
            complexityScore = 0.2; // 기타 펀드는 가장 단순함
        }
        double finalScore = getFinalScore(yieldScore, costScore, riskScore, liquidityScore, complexityScore, "fund");
        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
            "liquidityScore", liquidityScore, "complexityScore", complexityScore);
    }

    public static Map<String, Double> calculatePensionScore(PensionAlgorithmResponseDTO pension) {
        double finalScore;
        Map<String, Double> scores;
        double standardRate = 2.5; // 기준 금리 추후 api로 변경 예정
        double minLastYearProfit = -15.0;
        double maxLastYearProfit = 33.1;
        if (pension == null) {
            return null;
        }
        if(pension.getId().charAt(0) == 'A'){ //공격형 연금일 경우 펀드와 계산 방식 유사
            double rate = pension.getRate();
            double yieldScore = (rate - standardRate) / (maxLastYearProfit - minLastYearProfit); // 수익성 점수
            double riskScore = getRiskScore(pension.getRisk()); // 위험성 점수
            double costScore = (pension.getTER() - MIN_FUND_TER) / (MAX_FUND_TER - MIN_FUND_TER); // 비용 점수
            double liquidityScore = 1.0; // 유동성 점수는 1로 고정
            double complexityScore;
            if(pension.getPensionKind().contains("주식")) complexityScore = 0.8;
            else if(pension.getPensionType().contains("혼합")) complexityScore = 0.6;
            else if(pension.getPensionType().contains("펀드")) complexityScore = 0.4;
            else if(pension.getPensionType().contains("채권")) complexityScore = 0.2;
            else complexityScore = 0.1; // 기타 연금은 가장 단순한 것으로 간주
            finalScore = getFinalScore(yieldScore, costScore, riskScore, liquidityScore, complexityScore, "pension");
            scores = Map.of("algoCode", finalScore,
                "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
                "liquidityScore", liquidityScore, "complexityScore", complexityScore);

        } else { //안정형 연금일 경우 예적금과 계산 방식 유사
            double rate = pension.getRate();
            double yieldScore = (rate - standardRate) / (maxLastYearProfit - minLastYearProfit); // 수익성 점수
            double riskScore = getRiskScore(6); // 위험성 점수는 1.0로 고정
            double costScore = 0.0; // 비용 점수는 현재 0으로 설정
            double liquidityScore = 1.0 - pension.getSaveTerm()/MAX_SAVE_TERM;
            // 복잡성 점수는 예적금보다는 높지만 연금 상품이므로 단순하게 설정
            double complexityScore = 0.5 + getJoinDenyScore(pension.getFSector());
            finalScore = getFinalScore(yieldScore, costScore, riskScore, liquidityScore, complexityScore, "pension");
            scores = Map.of("algoCode", finalScore,
                "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
                "liquidityScore", liquidityScore, "complexityScore", complexityScore);
        }
        return scores;
    }

    public static Map<String,Double> calculateForexScore(ForexAlgorithmResponseDTO forex) {
        if(forex == null) {
            return null;
        }
        double yieldScore = getProfitScore(forex.getRate());
        double riskScore = getRiskScore(forex.getRisk()); // 위험성 점수는 1.0로 고정
        double costScore = 1.5/ MAX_FUND_TER;
        double liquidityScore = ((forex.getMinSaveTerm() - MIN_SAVE_TERM) / (MAX_SAVE_TERM - MIN_SAVE_TERM)) * 0.9 + (forex.getAutoRenew().equals("Y") ? 0.1 : 0.0); // 유동성 점수 계산
        double complexityScore = 0 + getJoinDenyScore(forex.getTaxRefund()) + (forex.getProtect() == 1 ? 0.1 : 0.0); // 보호 여부에 따라 복잡성 점수 추가
        double finalScore = getFinalScore(yieldScore, costScore, riskScore, liquidityScore, complexityScore, "forex");
        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
            "liquidityScore", liquidityScore, "complexityScore", complexityScore);
    }
}
