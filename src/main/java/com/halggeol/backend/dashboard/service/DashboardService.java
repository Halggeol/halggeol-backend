package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface DashboardService {

    DashboardResponseDTO getDashboardData(String userId);
}
