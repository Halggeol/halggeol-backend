package com.halggeol.backend.common;

import com.halggeol.backend.common.enums.DBType;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HealthStatusHolder {
    private final Map<DBType, StatusInfo> statusMap = new ConcurrentHashMap<>();

    @Getter
    public static class StatusInfo {
        private final String status; // "UP", "DOWN"
        private final String lastErrorMessage;
        private final LocalDateTime lastChecked;

        public StatusInfo(String status, String lastErrorMessage) {
            this.status = status;
            this.lastErrorMessage = lastErrorMessage;
            this.lastChecked = LocalDateTime.now();
        }
    }

    /**
     * 서비스 상태 확인 성공 시 호출
     */
    public void updateStatusOnSuccess(DBType dbtype) {
        statusMap.put(dbtype, new StatusInfo("UP", null));
    }

    /**
     * 서비스 상태 확인 실패 시 호출
     */
    public void updateStatusOnFailure(DBType dbtype, String errorMessage) {
        statusMap.put(dbtype, new StatusInfo("DOWN", errorMessage));
    }

    /**
     * 모든 서비스의 현재 상태를 반환
     */
    public Map<DBType, StatusInfo> getAllStatuses() {
        return Map.copyOf(statusMap);
    }
}