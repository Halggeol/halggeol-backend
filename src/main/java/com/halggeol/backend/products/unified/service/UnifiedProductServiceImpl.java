package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import com.halggeol.backend.products.unified.mapper.UnifiedProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnifiedProductServiceImpl implements UnifiedProductService {

    private final UnifiedProductMapper unifiedProductMapper;

    @Override
    public List<UnifiedProductResponseDTO> getFilteredProducts(
//        String sort,
//        String keyword,
        String type,
        Integer fSector,
        Integer saveTerm,
        String minAmount
    ) {
        return unifiedProductMapper.selectFilteredProducts(
            type, fSector, saveTerm, minAmount
        );
    }

    @Override
    public List<UnifiedProductRegretRankingResponseDTO> getRegretRankingProducts() {
        return unifiedProductMapper.selectUnifiedProductsOrderByRegretCnt();
    }

}
