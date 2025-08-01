package com.halggeol.backend.products.unified.mapper;

import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UnifiedProductMapper {
    List<UnifiedProductRegretRankingResponseDTO> selectUnifiedProductsOrderByRegretCnt();

    List<UnifiedProductResponseDTO> selectFilteredProducts(
        @Param("sort") String sort,
        @Param("keyword") String keyword,
        @Param("type") String type,
        @Param("fSector") Integer fSector,
        @Param("saveTerm") Integer saveTerm,
        @Param("minAmount") String minAmount
    );
}
