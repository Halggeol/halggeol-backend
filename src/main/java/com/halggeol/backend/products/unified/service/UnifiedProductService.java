package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import java.util.List;

public interface UnifiedProductService {

    List<UnifiedProductRegretRankingResponseDTO> getRegretRankingProducts();
    List<UnifiedProductResponseDTO> getAllProducts();
}
