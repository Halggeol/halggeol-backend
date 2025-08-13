package com.halggeol.backend.insight.util.calculator.strategy;

import com.halggeol.backend.insight.domain.ProductType;
import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DepositProfitStrategy extends AbstractProfitCalculatorStrategy {

    private static final double INVESTMENT_RATIO = 0.5;
    private static final int DEFAULT_DURATION_MONTHS = 12;

    @Override
    public ProductType getProductType() {
        return ProductType.DEPOSIT;
    }

    @Override
    public long estimateInvestmentAmount(List<ProfitSimulationDTO> profitSimulations, Long maxLimit) {
        if (profitSimulations.isEmpty() || profitSimulations.get(0).getAsset() == null) return 0;
        Long asset = profitSimulations.get(0).getAsset(); // 추천 당시 자산
        long investment = Math.round(asset * INVESTMENT_RATIO);
        return maxLimit != null ? Math.min(investment, maxLimit) : investment; // 투자금이 최대 가입 금액보다 클 경우 최대 가입 금액을 투자금으로 설정
    }

    @Override
    protected List<ProfitSimulationDTO> doCalculate(
        ProfitCalculationInput input, long principal, ProfitSimulationDTO base,
        boolean isCompound, LocalDate baseDate, double rate
    ) {
        int duration = input.getSaveTerm() != null ? input.getSaveTerm() : DEFAULT_DURATION_MONTHS;

        List<ProfitSimulationDTO> resultList = new ArrayList<>();
        long profitAmount = 0;

        for (int month = 1; month <= duration; month++) {
            long totalProfit = isCompound ? calculateCompound(principal, rate, month) : calculateSimple(principal, rate, month);
            long monthlyProfit = totalProfit - profitAmount;
            profitAmount = totalProfit;

            resultList.add(
                ProfitSimulationDTO.builder()
                    .date(baseDate.plusMonths(month))
                    .profit(base.getProfit())
                    .asset(base.getAsset())
                    .lostInvestment(monthlyProfit)
                    .lostAsset(base.getAsset() + totalProfit)
                    .build()
            );
        }
        return resultList;
    }
}