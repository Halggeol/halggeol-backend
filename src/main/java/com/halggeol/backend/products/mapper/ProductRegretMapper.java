package com.halggeol.backend.products.mapper;

import com.halggeol.backend.products.dto.RegretRankingResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductRegretMapper {
    List<RegretRankingResponseDTO> getRegretRanking();
}
