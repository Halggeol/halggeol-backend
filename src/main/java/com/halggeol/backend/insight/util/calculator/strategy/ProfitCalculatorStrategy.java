package com.halggeol.backend.insight.util.calculator.strategy;

import com.halggeol.backend.insight.enums.ProductType;
import com.halggeol.backend.insight.dto.ProfitCalculationInput;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.util.List;

public interface ProfitCalculatorStrategy {
    long estimateInvestmentAmount(List<ProfitSimulationDTO> profitSimulations, Long maxLimit);
    List<ProfitSimulationDTO> calculate(ProfitCalculationInput input, long principal);
    ProductType getProductType();
}
