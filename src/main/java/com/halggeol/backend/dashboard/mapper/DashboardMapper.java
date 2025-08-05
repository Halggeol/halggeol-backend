package com.halggeol.backend.dashboard.mapper;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardPortfolioResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface DashboardMapper {
    @Cacheable(value = "userDashboardDataCache", key = "'avgRegretScore:' + #userId")
    Double getAvgRegretScoreByUserId(String userId);

    @Cacheable(value = "userDashboardDataCache", key = "'assets:' + #userId")
    List<DashboardAssetResponseDTO> getAssetsOneYearByUserId(String userId);

    @Cacheable(value = "userDashboardDataCache", key = "'portfolio:' + #userId")
    List<DashboardPortfolioResponseDTO> getPortfolioByUserId(String userId);

    @Cacheable(value = "userDashboardDataCache", key = "'feedbackRatio:' + #userId")
    Double getFeedbackRatioByUserId(String userId);
}
