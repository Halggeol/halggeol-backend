package com.halggeol.backend.products.unified.mapper;

import com.halggeol.backend.products.unified.dto.UnifiedProductDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UnifiedProductMapper {
    List<UnifiedProductDTO> selectAllUnifiedProducts();

    List<UnifiedProductRegretRankingResponseDTO> selectUnifiedProductsOrderByRegretCnt();
}
