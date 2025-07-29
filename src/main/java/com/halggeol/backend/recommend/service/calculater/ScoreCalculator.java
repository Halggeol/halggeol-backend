package com.halggeol.backend.recommend.service.calculater;

import com.halggeol.backend.recommend.dto.DepositAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ForexAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.FundAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.PensionAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.SavingsAlgorithmResponseDTO;
import java.util.Map;

public class ScoreCalculator {

    public static Map<String, Double> calculateDepositScore(DepositAlgorithmResponseDTO deposit) {
        double standardRate = 2.5; // 기준 금리 추후 api로 변경 예정
        if(deposit == null) {
            return null;
        }
        double primeRate = (standardRate - deposit.getRate())/(deposit.getPrimeRate()- deposit.getRate());
        double joinDenyScore = 0;
        if(deposit.getJoinDeny() == 1) joinDenyScore = 1;
        else if(deposit.getJoinDeny() == 2) joinDenyScore = 0.5;
        else if(deposit.getJoinDeny() == 3) joinDenyScore = 0.1;
        else if(deposit.getJoinDeny() == 4) joinDenyScore = 0.01;
        double finalScore = getFinalScore(deposit, primeRate, joinDenyScore);
        if(finalScore < 0) {
            finalScore = 0; // 점수는 음수가 될 수 없으므로 0으로 설정
        } else if(finalScore > 1) {
            finalScore = 1; // 점수는 1을 초과할 수 없으므로 1로 설정
        }
        double yieldScore = (deposit.getPrimeRate()-0.5) / 5.5; // 이율은 0.5부터 6.0까지의 범위로 가정
        double riskScore = 1.0; // 위험성 점수는 1.0로 고정
        double costScore = 1.0; // 비용 점수는 1.0로 고정
        double liquidityScore = 1 - (deposit.getMinSaveTerm()/ 60.0); // 유동성 점수는 최소 60개월로 가정
        double complexityScore = (deposit.getJoinDeny() == 1 ? 1.0 : 0.8); // 가입 거절 여부에 따라 복잡성 점수 설정
        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
            "liquidityScore", liquidityScore, "complexityScore", complexityScore);
    }

    private static double getFinalScore(DepositAlgorithmResponseDTO deposit, double primeRate,
        double joinDenyScore) {
        double maxLimitScore;
        if(deposit.getMaxLimit() == null || deposit.getMaxLimit() == 0) {
            maxLimitScore = 0; // 최고 한도가 없는 경우
        } else if(deposit.getMaxLimit() >= 1000000000) maxLimitScore = 1;
        else {
            maxLimitScore = Math.log10(deposit.getMaxLimit()) / Math.log10(1000000000);
        }
        int minSaveTerm = deposit.getMinSaveTerm();
        int maxSaveTerm = deposit.getMaxSaveTerm();

        double saveTermScore = 1 - (minSaveTerm/ (double) maxSaveTerm);
        double minimumCostScore = 0;
        if(deposit.getMinimumCost() == null || deposit.getMinimumCost().isEmpty()) {
            minimumCostScore = 1; // 가입금액이 없는 경우
        } else {
            if(deposit.getMaxLimit() == null || deposit.getMaxLimit() == 0) {
                minimumCostScore = 0;
            } else {
                minimumCostScore = 1 - (Double.parseDouble(deposit.getMinimumCost()) / deposit.getMaxLimit());
            }
        }
        double finalScore = (0.4 * primeRate + 0.1 * joinDenyScore + 0.1 * maxLimitScore +
            0.2 * saveTermScore + 0.2 * minimumCostScore);
        return finalScore;
    }

    public static Map<String, Double> calculateSavingsScore(SavingsAlgorithmResponseDTO savings){
        double standardRate = 2.5;
        if(savings == null) {
            return null;
        }
        double primeRate = (standardRate - savings.getRate()) / (savings.getPrimeRate() - savings.getRate());
        double joinDenyScore = 0;
        if(savings.getJoinDeny() == 1) joinDenyScore = 1;
        else if(savings.getJoinDeny() == 2) joinDenyScore = 0.5;
        else if(savings.getJoinDeny() == 3) joinDenyScore = 0.1;
        else if(savings.getJoinDeny() == 4) joinDenyScore = 0.01;
        double finalScore = getFinalScore(savings, primeRate, joinDenyScore);
        if(finalScore < 0) {
            finalScore = 0; // 점수는 음수가 될 수 없으므로 0으로 설정
        } else if(finalScore > 1) {
            finalScore = 1; // 점수는 1을 초과할 수 없으므로 1로 설정
        }
        double yieldScore = (savings.getPrimeRate() - 0.5) / 5.5; // 이율은 0.5부터 6.0까지의 범위로 가정
        double riskScore = 1.0; // 위험성 점수는 1.0로 고정
        double costScore = 1.0; // 비용 점수는 1.0로 고정
        double liquidityScore = 1 - (savings.getMinSaveTerm() / 60.0); // 유동성 점수는 최소 60개월로 가정
        double complexityScore = (savings.getJoinDeny() == 1 ? 1.0 : 0.8); // 가입 거절 여부에 따라 복잡성 점수 설정

        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
            "liquidityScore", liquidityScore, "complexityScore", complexityScore);
    }

    private static double getFinalScore(SavingsAlgorithmResponseDTO savings, double primeRate,
        double joinDenyScore) {
        double maxLimitScore;
        if(savings.getMaxLimit() == null || savings.getMaxLimit() == 0) {
            maxLimitScore = 0; // 최고 한도가 없는 경우
        } else if(savings.getMaxLimit() >= 1000000000) {
            maxLimitScore = 1;
        } else {
            maxLimitScore = Math.log10(savings.getMaxLimit()) / Math.log10(1000000000);
        }
        int minSaveTerm = savings.getMinSaveTerm();
        int maxSaveTerm = savings.getMaxSaveTerm();
        double saveTermScore = 1 - (minSaveTerm / (double) maxSaveTerm);
        double finalScore = (0.4 * primeRate + 0.1 * joinDenyScore + 0.1 * maxLimitScore +
            0.2 * saveTermScore + 0.2 * 1); // 가입금액은 SavingsAlgorithmResponseDTO에 없으므로 1로 설정
        if(finalScore < 0) {
            finalScore = 0; // 점수는 음수가 될 수 없으므로 0으로 설정
        } else if(finalScore > 1) {
            finalScore = 1; // 점수는 1을 초과할 수 없으므로 1로 설정
        }
        return finalScore;
    }

    public static Map<String, Double> calculateFundScore(FundAlgorithmResponseDTO fund){
        double minRate = -2.74;
        double maxRate = 14.4;
        double maxTER = 2.0;
        double maxFee = 1.3;
        double MaxMinimumCost = 100000;
        double MaxFundPriceMovement = 49.84; // 최대 펀드 가격 변동률
        double minFundPriceMovement = -26.27; // 최소 펀드 가격 변동률
        if(fund == null) {
            return null;
        }
        double rate = fund.getRate();
        double rateScore = (rate - minRate) / (maxRate - minRate);
        int investWarningGrade;
        if(fund.getInvestmentWarningGrade().contains("1")) {
            investWarningGrade = 1;
        } else if(fund.getInvestmentWarningGrade().contains("2")) {
            investWarningGrade = 2;
        } else if(fund.getInvestmentWarningGrade().contains("3")) {
            investWarningGrade = 3;
        } else if(fund.getInvestmentWarningGrade().contains("4")) {
            investWarningGrade = 4;
        } else if (fund.getInvestmentWarningGrade().contains("5")) {
            investWarningGrade = 5;
        } else if (fund.getInvestmentWarningGrade().contains("6")) {
            investWarningGrade = 6;
        } else {
            investWarningGrade = 0; // 투자경고등급이 없는 경우
        }
        double investWarningScore = (6- investWarningGrade) / 5.0;
        double TERScore = 1 - (fund.getTER()/ maxTER);
        double FeeScore = 1 - ((fund.getManagementFee()+ fund.getManagementFee())/ maxFee);
        double costScore = 1 - (Long.parseLong(fund.getMinimumCost())) / MaxMinimumCost;
        double investTypeScore = (fund.getInvestmentType().contains("적립") ? 1.0 : 0.5);// 적립식이면 1, 거치식이면 0.5
        double fundPriceMovementScore = (fund.getFundPriceMovement() - minFundPriceMovement) / (MaxFundPriceMovement - minFundPriceMovement);

        double finalScore = (0.4 * rateScore + 0.2 * investWarningScore + 0.15 * TERScore +
            0.1 * FeeScore + 0.05 * costScore + 0.05 * investTypeScore + 0.05 * fundPriceMovementScore);
        if(finalScore < 0) {
            finalScore = 0; // 점수는 음수가 될 수 없으므로 0으로 설정
        } else if(finalScore > 1) {
            finalScore = 1; // 점수는 1을 초과할 수 없으므로 1로 설정
        }
        double yieldScore = (fund.getRate() - minRate) / (maxRate - minRate); // 수익성 점수
        double riskScore = investWarningScore;
        double costScoreFinal = 1- (fund.getTER() + fund.getManagementFee() + fund.getUpfrontFee())/5.0; // 비용 점수
        double liquidityScore = 1 - (fund.getFundPriceMovement() / MaxFundPriceMovement); // 유동성 점수
        double complexityScore;
        if(fund.getTheme().contains("ETF") && fund.getTheme().contains("채권")) {
            complexityScore = 0.8; // ETF와 채권이 모두 포함된 경우
        } else if(fund.getTheme().contains("ETF") || fund.getTheme().contains("채권")) {
            complexityScore = 0.6; // ETF 또는 채권이 포함된 경우
        } else if(fund.getTheme().contains("배당")){
            complexityScore = 0.4; // 배당이 포함된 경우
        } else {
            complexityScore = 0.2; // 기타 테마인 경우
        }
        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScoreFinal,
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
        if(pension.getId().charAt(0) == 'A'){ //공격형 연금일 경우
            int riskLevel = pension.getRisk();
            double riskScore = (6 - riskLevel) / 5.0; // 위험 등급이 낮을수록 점수가 높음
            double rateScore = (pension.getLastYearProfit() - minLastYearProfit) / (maxLastYearProfit - minLastYearProfit);
            double categoryScore = 1.0; // 공격형 연금은 기본 점수 1.0
            double kindScore;
            if(pension.getPensionKind().contains("DC")) kindScore = 1.0;
            else if(pension.getPensionType().contains("펀드")) kindScore = 0.8;
            else if(pension.getPensionType().contains("IRP")) kindScore = 0.6;
            else if(pension.getPensionType().contains("보험")) kindScore = 0.4;
            else kindScore = 0.2; // 기타 연금 상품은 기본 점수 0.2

            finalScore = (0.4 * rateScore + 0.3 * riskScore + 0.2 * categoryScore + 0.1 * kindScore);
            //costScore의 판단 기준 마련 필요
            scores = Map.of("algoCode", finalScore,
                "yieldScore", rateScore, "riskScore", riskScore, "costScore", 1.0,
                "liquidityScore", 0.3, "complexityScore", 1 - kindScore);
        } else { //안정형 연금일 경우
            int riskLevel = pension.getRisk();
            double riskScore = (6 - riskLevel) / 5.0; // 위험 등급이 낮을수록 점수가 높음
            double minRateScore = (pension.getRate() - standardRate)/ standardRate; // 기준 금리 대비 점수
            double categoryScore = 0.0; // 안정 연금은 기본 점수 1.0
            double kindScore;
            if(pension.getPensionKind().contains("DC")) kindScore = 1.0;
            else if(pension.getPensionType().contains("펀드")) kindScore = 0.8;
            else if(pension.getPensionType().contains("IRP")) kindScore = 0.6;
            else if(pension.getPensionType().contains("보험")) kindScore = 0.4;
            else kindScore = 0.2;
            finalScore = (0.4 * minRateScore + 0.3 * riskScore + 0.2 * categoryScore + 0.1 * kindScore);
            //costScore의 판단 기준 마련 필요
            scores = Map.of("algoCode", finalScore,
                "yieldScore", minRateScore, "riskScore", riskScore, "costScore", 1.0,
                "liquidityScore", 0.4, "complexityScore", 1 - kindScore);
        }
        return scores;
    }

    public static Map<String,Double> calculateForexScore(ForexAlgorithmResponseDTO forex) {
        double standardRate = 2.5; // 기준 금리 추후 api로 변경 예정
        if(forex == null) {
            return null;
        }
        double rateScore = (standardRate - forex.getRate()) / (standardRate - 0); // 0은 최소 이율로 가정
        double taxRefundScore = 0;
        if(forex.getTaxRefund() == 1) {
            taxRefundScore = 1; // 세금 환급이 있는 경우
        } else if(forex.getTaxRefund() == 0) {
            taxRefundScore = 0.5; // 세금 환급이 없는 경우
        } else {
            taxRefundScore = 0.1; // 기타 경우
        }
        double protectScore = 0;
        if(forex.getProtect() == 1) {
            protectScore = 1; // 보호가 있는 경우
        } else if(forex.getProtect() == 0) {
            protectScore = 0.5; // 보호가 없는 경우
        } else {
            protectScore = 0.1; // 기타 경우
        }
        double currencyScore = 0;
        if(forex.getCurrency().contains("USD")) {
            currencyScore = 1; // USD는 가장 안정적인 통화로 가정
        } else if(forex.getCurrency().contains("EUR")) {
            currencyScore = 0.8; // EUR는 두 번째로 안정적인 통화로 가정
        } else if(forex.getCurrency().contains("JPY")) {
            currencyScore = 0.6; // JPY는 세 번째로 안정적인 통화로 가정
        } else {
            currencyScore = 0.4; // 기타 통화는 기본 점수 0.4
        }
        double regFundScore = 0;
        if(forex.getRegFund() == null || forex.getRegFund() == 0) {
            regFundScore = 0; // 등록 자산이 없는 경우
        } else if(forex.getRegFund() >= 1000000000) {
            regFundScore = 1; // 등록 자산이 10억 이상인 경우
        } else {
            regFundScore = (double)Math.log10(forex.getRegFund()) / Math.log10(1000000000); // 10억을 기준으로 로그 스케일로 변환
        }
        double finalScore = (0.4 * rateScore + 0.2 * taxRefundScore + 0.2 * protectScore +
            0.1 * currencyScore + 0.1 * regFundScore);
        if(finalScore < 0) {
            finalScore = 0; // 점수는 음수가 될 수 없으므로 0으로 설정
        } else if(finalScore > 1) {
            finalScore = 1; // 점수는 1을 초과할 수 없으므로 1로 설정
        }
        double yieldScore = (forex.getRate() + (forex.getTaxRefund()/2.0) - 0.5) / 5.5; // 수익성 점수
        double riskScore = (forex.getProtect() == 1 ? 1.0 : 0.8); // 위험성 점수
        double costScore = 0.9; // 이거는 환전 수수료율 정규화 해서 써야하는데...? 일단 임의로 0.9로 설정;
        double liquidityScore;
        if(forex.getAutoRenew().contains("Y") && forex.getExtraDeposit()==1) {
            liquidityScore = 1.0; // 자동 연장 및 추가 입금이 가능한 경우
        } else if(forex.getAutoRenew().contains("N") && forex.getExtraDeposit()==0) {
            liquidityScore = 0.2; // 자동 연장 및 추가 입금이 불가능한 경우
        } else {
            liquidityScore = 0.5; // 기타 경우
        }
        double complexityScore;
        if(forex.getCurrency().contains("USD")) {
            complexityScore = 0.8; // USD는 가장 단순한 통화로 가정
        } else if(forex.getCurrency().contains("EUR")) {
            complexityScore = 0.6; // EUR는 두 번째로 단순한 통화로 가정
        } else if(forex.getCurrency().contains("JPY")) {
            complexityScore = 0.4; // JPY는 세 번째로 단순한 통화로 가정
        } else {
            complexityScore = 0.2; // 기타 통화는 기본 점수 0.2
        }

        return Map.of("algoCode", finalScore,
            "yieldScore", yieldScore, "riskScore", riskScore, "costScore", costScore,
            "liquidityScore", liquidityScore, "complexityScore", complexityScore);
    }
}
