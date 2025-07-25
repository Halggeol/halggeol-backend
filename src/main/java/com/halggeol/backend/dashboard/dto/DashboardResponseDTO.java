package com.halggeol.backend.dashboard.dto;

import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {

    private int avgRegretScore;
//    private List<AssetDTO> assets;
//    private List<PortfolioDTO> portfolio;
//    private List<RegretRankingDTO> regretRanking;
    private String userName;
    private List<RecommendResponseDTO> recommendItems;
}
