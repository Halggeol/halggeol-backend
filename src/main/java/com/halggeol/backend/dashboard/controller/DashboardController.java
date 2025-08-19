package com.halggeol.backend.dashboard.controller;

import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.dashboard.service.DashboardService;
import com.halggeol.backend.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getDashboard(@AuthenticationPrincipal CustomUser user){
        DashboardResponseDTO response = dashboardService.getDashboardData(
        user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
