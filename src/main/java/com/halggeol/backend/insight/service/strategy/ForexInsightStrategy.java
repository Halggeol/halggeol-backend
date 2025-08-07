package com.halggeol.backend.insight.service.strategy;

import com.halggeol.backend.insight.domain.ProductType;
import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import com.halggeol.backend.insight.mapper.InsightDetailMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForexInsightStrategy implements InsightDetailStrategy {
    private final InsightDetailMapper mapper;

    @Override
    public ProductType getProductType() {
        return ProductType.FOREX;
    }

    @Override
    public InsightDetailResponseDTO fetchProductInfo(int round, String productId, int userId) {
        return mapper.getForexInfo(round, productId, userId);
    }

    @Override
    public List<ProfitSimulationDTO> fetchSimulation(int round, String productId, int userId, LocalDate recDate, LocalDate anlzDate) {
        return null;
    }
}
