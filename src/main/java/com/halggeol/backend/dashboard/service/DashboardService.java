package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.domain.CustomUser;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface DashboardService {
    @Transactional(readOnly = true)
    DashboardResponseDTO getDashboardData(@AuthenticationPrincipal CustomUser user);

    @Transactional
    List<DashboardAssetResponseDTO> getAssetsOneYearByUserId(String userId);
}
