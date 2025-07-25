package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProductDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import java.util.List;

public interface UnifiedProductService {
    List<UnifiedProductDTO> getAllProducts();

    List<UnifiedProductRegretRankingResponseDTO> getRegretRankingProducts();
}
