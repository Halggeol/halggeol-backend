package com.halggeol.backend.common;

import com.halggeol.backend.common.enums.DBType;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HealthStatusHolder {

    // 스레드 환경에서 안전하게 사용하기 위해 ConcurrentHashMap 사용
    private final Map<DBType, StatusInfo> statusMap = new ConcurrentHashMap<>();

    /**
     * 각 서비스의 상태 정보를 담는 간단한 내부 클래스
     */
    @Getter // Lombok을 사용해 Getter 메소드 자동 생성
    public static class StatusInfo {
        private final String status; // "UP", "DOWN" 등의 문자열 상태
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