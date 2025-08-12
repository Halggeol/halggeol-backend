package com.halggeol.backend.common.controller;

import com.halggeol.backend.common.HealthStatusHolder;
import com.halggeol.backend.common.HealthStatusHolder.StatusInfo;
import com.halggeol.backend.common.enums.DBType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemHealthController {

    private final HealthStatusHolder healthStatusHolder;

    @GetMapping("/health")
    public Map<DBType, StatusInfo> getSystemHealth() {
        return healthStatusHolder.getAllStatuses();
    }
}
