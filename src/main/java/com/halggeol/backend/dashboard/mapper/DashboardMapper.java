package com.halggeol.backend.dashboard.mapper;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardPortfolioResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DashboardMapper {
    Double getAvgRegretScoreByUserId(String userId);
    List<DashboardAssetResponseDTO> getAssetsOneYearByUserId(String userId);
    List<DashboardPortfolioResponseDTO> getPortfolioByUserId(String userId);
}
