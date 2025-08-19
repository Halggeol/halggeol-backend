package com.halggeol.backend.insight.util.calculator.strategy;

import com.halggeol.backend.insight.enums.ProductType;
import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SavingsProfitStrategy extends AbstractProfitCalculatorStrategy {
    private static final double INVESTMENT_RATIO = 0.5;
    private static final int MONTHS_IN_YEAR = 12;

    @Override
    public ProductType getProductType() {
        return ProductType.SAVINGS;
    }

    @Override
    public long estimateInvestmentAmount(List<ProfitSimulationDTO> initialSimulations, Long maxLimit) {
        if (initialSimulations.isEmpty() || initialSimulations.get(0).getAsset() == null) return 0;
        long asset = initialSimulations.get(0).getAsset();
        long investment = Math.round(asset * INVESTMENT_RATIO) / MONTHS_IN_YEAR;
        return maxLimit != null ? Math.min(investment, maxLimit) : investment;
    }

    @Override
    protected List<ProfitSimulationDTO> doCalculate(
        ProfitCalculationInput input, long principal, ProfitSimulationDTO base,
        boolean isCompound, Date baseDate, double rate
    ) {
        List<ProfitSimulationDTO> resultList = new ArrayList<>();
        long profitAmount = 0;

        // 시작점 이후
        for (int month = 1; month <= MONTHS_IN_YEAR; month++) {
//            LocalDate date = baseDate.plusMonths(month);
            Date date = baseDate != null ? Date.valueOf(baseDate.toLocalDate().plusMonths(month)) : Date.valueOf(LocalDate.now().plusMonths(month));
            long totalProfit = 0;
            for (int i = 1; i <= month; i++) {
                int remain = MONTHS_IN_YEAR - i + 1;
                totalProfit += isCompound ? calculateCompound(principal, rate, remain) : calculateSimple(principal, rate, remain);
            }
            long monthlyProfit = totalProfit - profitAmount;
            profitAmount = totalProfit;

            resultList.add(
                ProfitSimulationDTO.builder()
                    .date(date)
                    .profit(base.getProfit())
                    .asset(base.getAsset())
                    .lostInvestment(monthlyProfit)
                    .lostAsset(base.getAsset() + (principal * month) + totalProfit)
                    .build()
            );
        }
        return resultList;
    }
}