package com.halggeol.backend.insight.util.calculator;

import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfitCalculator {
    // todo. 전략 패턴 적용으로 수정하기
    public static List<ProfitSimulationDTO>  calculateProfitSimulations(ProfitCalculationInput input) {
        char prefix = input.getProductId().charAt(0);
        long principal = estimateInvestmentAmount(input.getProfitSimulations(), input.getMaxLimit(), prefix);

        if (prefix == 'D' || prefix == 'C') {
            return calculateProfitForConservative(input, principal);
        } else if (prefix == 'S') {
            return calculateProfitForSavings(input, principal);
        } else if (prefix == 'F' || prefix == 'A') {
            return calculateProfitForAggressive(input, principal);
        }

        return new ArrayList<>();
    }

    // 투자금
    private static long estimateInvestmentAmount(List<ProfitSimulationDTO> profitSimulations, Long maxLimit, char prefix) {
        Long asset = profitSimulations.get(0).getAsset(); // 추천 당시 자산

        // 안정형 전체 재산의 0.5, 공격형은 0.2로 잡음
        long investment = 0;
        if (prefix == 'D' || prefix == 'C') {
            investment = Math.round(asset * 0.5);
        } else if (prefix == 'S') {
            investment = Math.round(asset * 0.5) / 12;
        } else if (prefix == 'F' || prefix == 'A') {
            investment = Math.round(asset * 0.2);
        }

        return maxLimit != null ? Math.min(investment, maxLimit) : investment; // 투자금이 최대 가입 금액보다 클 경우 최대 가입 금액을 투자금으로 설정
    }

    // 수익 계산
    // 안정형 상품: 기존 자산 변동 고려 X, 추천 시점 자산 스냅샷 ->  매월 누적 수익을 복리/단리로 계산
    // 예금 상품 전용
    private static List<ProfitSimulationDTO> calculateProfitForConservative(ProfitCalculationInput input, long principal) {
        List<ProfitSimulationDTO> resultList = new ArrayList<>();

        List<ProfitSimulationDTO> profits = input.getProfitSimulations();
        int duration = input.getSaveTerm() != null ? input.getSaveTerm() : 12; // C인 경우 가입 기간을 가져 옴, 기본값 12개월로 설정
        boolean isCompound = Boolean.TRUE.equals(input.getIsCompound());

        ProfitSimulationDTO base = profits.get(0);
        LocalDate baseDate = base.getDate();
        double rate = base.getProfit() != null ? base.getProfit() / 100.0 : 0.0;

        long profitAmount = 0;
        for (int month = 1; month <= duration; month++) {
            LocalDate nxtDate = baseDate.plusMonths(month);
            long totalProfit = isCompound ? calculateCompound(principal, rate, month) : calculateSimple(principal, rate, month);
            long monthlyProfit = totalProfit - profitAmount;
            profitAmount = totalProfit;

            long totalAsset = principal + totalProfit;

            ProfitSimulationDTO simulation = new ProfitSimulationDTO();
            simulation.setDate(nxtDate);
            simulation.setProfit(base.getProfit());
            simulation.setAsset(base.getAsset());

            simulation.setLostInvestment(monthlyProfit);
            simulation.setLostAsset(totalAsset);

            resultList.add(simulation);
        }
        return resultList;
    }
    // 적금 상품 전용
    private static List<ProfitSimulationDTO> calculateProfitForSavings(ProfitCalculationInput input, long principal) {
        List<ProfitSimulationDTO> resultList = new ArrayList<>();

        int duration = 12;
        boolean isCompound = Boolean.TRUE.equals(input.getIsCompound());

        ProfitSimulationDTO base = input.getProfitSimulations().get(0);
        LocalDate baseDate = base.getDate();
        double rate = base.getProfit() != null ? base.getProfit() / 100.0 : 0.0;

        long totalPrincipal = 0;
        long profitAmount = 0;
        for (int month = 1; month <= duration; month++) {
            LocalDate date = baseDate.plusMonths(month);
            totalPrincipal += principal;

            long totalProfit = 0;
            for (int i = 1; i <= month; i++) {
                int remain = duration - i + 1;
                totalProfit += isCompound ? calculateCompound(principal, rate, remain) : calculateSimple(principal, rate, remain);
            }

            long monthlyProfit = totalProfit - profitAmount;
            profitAmount = totalProfit;

            ProfitSimulationDTO simulation = new ProfitSimulationDTO();
            simulation.setDate(date);
            simulation.setProfit(base.getProfit());
            simulation.setAsset(base.getAsset());
            simulation.setLostInvestment(monthlyProfit);
            simulation.setLostAsset(totalPrincipal+totalProfit);

            resultList.add(simulation);
        }
        return resultList;
    }

    private static long calculateCompound(long principal, double rate, int duration) {
        double monthlyRate = rate / 12.0;
        double result = principal * (Math.pow(1 + monthlyRate, duration) - 1); // 월복리
        return Math.round(result);
    }
    private static long calculateSimple(long principal, double rate, int duration) {
        double result = principal * rate * duration / 12.0; // 단리
        return Math.round(result);
    }

    // 공격형 상품
    private static List<ProfitSimulationDTO> calculateProfitForAggressive(ProfitCalculationInput input, long principal) {
        List<ProfitSimulationDTO> profits = input.getProfitSimulations();
        float ter = input.getTER() != null ? input.getTER() : 0f;
        float upfrontFee = input.getUpfrontFee() != null ? input.getUpfrontFee() : 0f;

        List<ProfitSimulationDTO> resultList = new ArrayList<>();

        // 이자=(투자금*(1-선취수수료))*(1+(이율-TER))^n
        double curPrincipal = principal - Math.round(principal * upfrontFee / 100.0); // 선취 수수료 차감

        for (ProfitSimulationDTO profit : profits) {
            double profitRate = profit.getProfit() != null ? profit.getProfit() : 0.0;
            double dailyProfit = curPrincipal * profitRate;

            double terFee = curPrincipal * (ter / 100.0) / 365.0; // 일단위 TER 계산
            curPrincipal += dailyProfit - terFee;

            if (curPrincipal < 0) curPrincipal = 0; // curPrincipal이 0 이하가 되는 상황 방지

            ProfitSimulationDTO simulation = new ProfitSimulationDTO();
            simulation.setDate(profit.getDate());
            simulation.setProfit(profit.getProfit());
            simulation.setAsset(profit.getAsset());

            simulation.setLostInvestment(Math.round(dailyProfit - terFee));
            simulation.setLostAsset(Math.round(profit.getAsset() - principal + curPrincipal));

            resultList.add(simulation);
        }
        return resultList;
    }
}
