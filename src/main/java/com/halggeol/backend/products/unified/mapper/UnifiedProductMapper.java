package com.halggeol.backend.products.unified.mapper;

import com.halggeol.backend.products.unified.dto.UnifiedProducResponsetDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UnifiedProductMapper {
    List<UnifiedProducResponsetDTO> selectAllUnifiedProducts();
}
