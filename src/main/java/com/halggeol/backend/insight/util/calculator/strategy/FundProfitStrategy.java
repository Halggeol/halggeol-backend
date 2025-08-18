package com.halggeol.backend.insight.util.calculator.strategy;

import com.halggeol.backend.insight.domain.ProductType;
import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FundProfitStrategy implements ProfitCalculatorStrategy {
    private static final double INVESTMENT_RATIO = 0.2;
    private static final int DAYS_IN_YEAR = 365;

    @Override
    public ProductType getProductType() {
        return ProductType.FUND;
    }

    @Override
    public long estimateInvestmentAmount(List<ProfitSimulationDTO> initialSimulations, Long maxLimit) {
        if (initialSimulations.isEmpty() || initialSimulations.get(0).getAsset() == null) return 0;
        long asset = initialSimulations.get(0).getAsset();
        long investment = Math.round(asset * INVESTMENT_RATIO);
        return maxLimit != null ? Math.min(investment, maxLimit) : investment;
    }

    @Override
    public List<ProfitSimulationDTO> calculate(ProfitCalculationInput input, long principal) {
        List<ProfitSimulationDTO> resultList = new ArrayList<>();
        List<ProfitSimulationDTO> profits = input.getProfitSimulations();
        if (profits == null || profits.isEmpty()) return resultList;

        float ter = input.getTER() != null ? input.getTER() : 0f;
        float upfrontFee = input.getUpfrontFee() != null ? input.getUpfrontFee() : 0f;

        // 시작점
        ProfitSimulationDTO base = profits.get(0);
//        for(int i = 1; i < profits.size(); i++) {
//            System.out.println("date: " + profits.get(i).getDate() + ", profit: " + profits.get(i).getProfit() + ", asset: " + profits.get(i).getAsset());
//        }
        resultList.add(createInitialState(base));

        double actualPrincipal = principal * (1 - upfrontFee / 100.0);
        double currentValuation = actualPrincipal;

        for (int i = 1; i < profits.size(); i++) {
            ProfitSimulationDTO profitData = profits.get(i);
            if(profitData.getAsset() == null || profitData.getDate() == null) break;
            double profitRate = profitData.getProfit() != null ? profitData.getProfit() / 100.0 : 0.0;
            double dailyProfit = currentValuation * profitRate;
            double terFee = currentValuation * ter / DAYS_IN_YEAR;

            currentValuation += dailyProfit - terFee;
            if (currentValuation < 0) {
                currentValuation = 0;
            }

            Long totalProfit = Math.round(currentValuation - actualPrincipal);

            resultList.add(
                ProfitSimulationDTO.builder()
                    .date(profitData.getDate())
                    .profit(profitData.getProfit())
                    .asset(profitData.getAsset())
                    .lostInvestment(Math.round(dailyProfit - terFee)) // 일일 순수익
                    .lostAsset(profitData.getAsset() + totalProfit)
                    .build()
            );
        }
        return resultList;
    }

    private ProfitSimulationDTO createInitialState(ProfitSimulationDTO base) {
        return ProfitSimulationDTO.builder()
            .date(base.getDate())
            .asset(base.getAsset())
            .lostAsset(base.getAsset())
            .lostInvestment(0L)
            .profit(base.getProfit())
            .build();
    }
}