package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProducResponsetDTO;
import com.halggeol.backend.products.unified.mapper.UnifiedProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnifiedProductServiceImpl implements UnifiedProductService {

    private final UnifiedProductMapper unifiedProductMapper;

    @Override
    public List<UnifiedProducResponsetDTO> getAllProducts() {
        return unifiedProductMapper.selectAllUnifiedProducts();
    }

}
