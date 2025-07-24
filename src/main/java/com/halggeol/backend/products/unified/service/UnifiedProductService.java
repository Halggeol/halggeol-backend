package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import java.util.List;

public interface UnifiedProductService {
    List<UnifiedProductResponseDTO> getAllProducts();
}
