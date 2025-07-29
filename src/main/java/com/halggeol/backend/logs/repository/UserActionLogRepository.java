package com.halggeol.backend.logs.repository;

import com.halggeol.backend.domain.UserActionLog;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserActionLogRepository extends MongoRepository<UserActionLog, String> {

    // 이 함수는 특정 시간 이후에 발생한 모든 사용자 액션 로그를 조회합니다.
    List<UserActionLog> findAllByTimestampAfter(Date timestampAfter);
}
