package com.halggeol.backend.insight.service.strategy;

import com.halggeol.backend.insight.domain.ProductType;
import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;

import java.time.LocalDate;
import java.util.List;

public interface InsightDetailStrategy {
    ProductType getProductType();
    InsightDetailResponseDTO fetchProductInfo(int round, String productId, int userId);
    List<ProfitSimulationDTO> fetchSimulation(int round, String productId, int userId, LocalDate recDate, LocalDate anlzDate);
}
