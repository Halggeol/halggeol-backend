package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProducResponsetDTO;
import java.util.List;

public interface UnifiedProductService {
    List<UnifiedProducResponsetDTO> getAllProducts();
}
