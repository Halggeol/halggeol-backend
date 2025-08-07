package com.halggeol.backend.recommend.service;

import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.dto.UserVectorResponseDTO;
import com.halggeol.backend.recommend.service.RecommendServiceImpl.Recommendation;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import java.util.List;

public interface RecommendService {

    public void updateAlgoCode();

    public void updateRecommendation();

    public List<Recommendation> getSimilarProducts(String productId);

//  public List<Recommendation> recommendProducts(String userId);

    public List<RecommendResponseDTO> getRecommendProducts(String userId);

    public void updateRecommendationByUserId(String userId);

    public Double getProductMatchScore(String productId, String userId);

    public UserVectorResponseDTO initUserVector(TendencySurveyRequestDTO tendencySurveyRequestDTO);
}
