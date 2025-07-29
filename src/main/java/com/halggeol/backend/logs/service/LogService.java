package com.halggeol.backend.logs.service;


import com.halggeol.backend.domain.UserActionLog;
import com.halggeol.backend.logs.repository.UserActionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final UserActionLogRepository repository;

    public void buildLog(String action, String productId, Integer userId) {
        // 로그를 생성하는 메소드
        UserActionLog log = new UserActionLog();
        log.setActionType(action);
        log.setProductId(productId);
        log.setUserId(userId);
        log.setTimestamp(new java.util.Date());
        // 추가적인 정보 설정 (예: userId, productId 등)

        // 로그 저장
        logAction(log);
    }

    public void logAction(UserActionLog log) {
        // 로그를 저장하는 메소드
        repository.save(log);
    }
}
