package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface DashboardService {

    DashboardResponseDTO getDashboardData(String userId);

    @Transactional
    List<DashboardAssetResponseDTO> getAssetsOneYearByUserId(String userId);
}
