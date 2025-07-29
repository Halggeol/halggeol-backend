package com.halggeol.backend.recommend.service;

import com.halggeol.backend.recommend.dto.ProductVectorResponseDTO;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.service.RecommendServiceImpl.Recommendation;
import java.util.List;

public interface RecommendService {

    public void updateAlgoCode();

    public void updateRecommendation();

    public List<Recommendation> similarProducts(ProductVectorResponseDTO productVector, List<ProductVectorResponseDTO> productVectors);

//    public List<Recommendation> recommendProducts(String userId);

    public List<RecommendResponseDTO> getRecommendProducts(String userId);
}
