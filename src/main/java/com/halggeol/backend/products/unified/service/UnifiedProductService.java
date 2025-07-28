package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;

import java.util.List;

public interface UnifiedProductService {
    List<UnifiedProductResponseDTO> getFilteredProducts(
        String sort,
        String keyword,
        String type,
        Integer fSector,
        Integer saveTerm,
        String minAmount);

    List<UnifiedProductRegretRankingResponseDTO> getRegretRankingProducts();
}
