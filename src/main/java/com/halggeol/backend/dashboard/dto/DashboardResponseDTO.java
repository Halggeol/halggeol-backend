package com.halggeol.backend.dashboard.dto;

import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO implements Serializable {

    private Double avgRegretScore;
    private List<DashboardAssetResponseDTO> assets;
    private List<DashboardPortfolioResponseDTO> portfolio;
    private List<UnifiedProductRegretRankingResponseDTO> regretRanking;
    private String userName;
    private List<RecommendResponseDTO> recommendItems;
    private Double feedbackRatio;
}
