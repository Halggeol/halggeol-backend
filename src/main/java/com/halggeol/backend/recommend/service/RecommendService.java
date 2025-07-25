package com.halggeol.backend.recommend.service;

import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import java.util.List;
import java.util.Map;

public interface RecommendService {

    List<RecommendResponseDTO> getRecommendProducts(String userId);
}
