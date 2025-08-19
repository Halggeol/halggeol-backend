package com.halggeol.backend.insight.service.strategy;

import com.halggeol.backend.insight.enums.ProductType;
import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import com.halggeol.backend.insight.mapper.InsightDetailMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavingsInsightStrategy implements InsightDetailStrategy {
    private final InsightDetailMapper mapper;

    @Override
    public ProductType getProductType() {
        return ProductType.SAVINGS;
    }

    @Override
    public InsightDetailResponseDTO fetchProductInfo(int round, String productId, int userId) {
        return mapper.getSavingsInfo(round, productId, userId);
    }

    @Override
    public List<ProfitSimulationDTO> fetchSimulation(int round, String productId, int userId, LocalDate recDate, LocalDate anlzDate) {
        return mapper.getProfitSimulation(round, productId, userId, "savings", recDate, anlzDate);
    }
}
