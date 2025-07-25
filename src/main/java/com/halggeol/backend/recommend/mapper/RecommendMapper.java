package com.halggeol.backend.recommend.mapper;

import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecommendMapper {
    List<RecommendResponseDTO> selectRecommendProducts(String userId);
}
