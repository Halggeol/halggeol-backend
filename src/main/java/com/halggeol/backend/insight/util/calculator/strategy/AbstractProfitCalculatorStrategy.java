package com.halggeol.backend.insight.util.calculator.strategy;

import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProfitCalculatorStrategy implements ProfitCalculatorStrategy {
    @Override
    public final List<ProfitSimulationDTO> calculate(ProfitCalculationInput input, long principal) {
        List<ProfitSimulationDTO> resultList = new ArrayList<>();
        List<ProfitSimulationDTO> profits = input.getProfitSimulations();
        if (profits == null || profits.isEmpty())
            return resultList;

        // 시작점
        ProfitSimulationDTO base = profits.get(0);
        resultList.add(createInitialState(base));
        boolean isCompound = input.getIsCompound();
//        LocalDate baseDate = base.getDate();
        Date baseDate = base.getDate() != null ? base.getDate() : Date.valueOf(LocalDate.now());
        double rate = base.getProfit() != null ? base.getProfit() / 100.0 : 0.0;

        List<ProfitSimulationDTO> calculatedPart = doCalculate(input, principal, base, isCompound, baseDate, rate);
        resultList.addAll(calculatedPart);

        return resultList;
    }

    protected abstract List<ProfitSimulationDTO> doCalculate(
        ProfitCalculationInput input, long principal, ProfitSimulationDTO base,
        boolean isCompound, Date baseDate, double rate
    );

    protected ProfitSimulationDTO createInitialState(ProfitSimulationDTO base) {
        return ProfitSimulationDTO.builder()
            .date(base.getDate())
            .asset(base.getAsset())
            .lostAsset(base.getAsset())
            .lostInvestment(0L)
            .profit(base.getProfit())
            .build();
    }

    protected long calculateCompound(long principal, double rate, int durationInMonths) {
        double monthlyRate = rate / 12.0;
        double result = principal * (Math.pow(1 + monthlyRate, durationInMonths) - 1);
        return Math.round(result);
    }

    protected long calculateSimple(long principal, double rate, int durationInMonths) {
        double result = principal * rate * durationInMonths / 12.0;
        return Math.round(result);
    }
}
