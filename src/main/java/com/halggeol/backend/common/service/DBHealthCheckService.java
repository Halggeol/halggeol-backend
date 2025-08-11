package com.halggeol.backend.common.service;

import lombok.RequiredArgsConstructor;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.elasticsearch._types.HealthStatus;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class DBHealthCheckService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MongoTemplate mongoTemplate;
    private final ElasticsearchClient elasticsearchClient;
    private final DataSource dataSource;

    /**
     * 5분마다 모든 외부 서비스의 상태를 체크합니다.
     */
    @Scheduled(fixedRate = 30000)
    public void checkAllExternalServices() {
        log.info("--- Starting external services heartbeat check ---");

        checkRedis();
        checkRds();
        checkMongoDB();
        checkElasticsearch();

        log.info("--- Finished external services heartbeat check ---");
    }

    /**
     * Redis 서버 상태를 PING-PONG으로 확인합니다.
     */
    private void checkRedis() {
        try {
            String response = redisTemplate.execute((RedisConnection connection) -> connection.ping());
            if ("PONG".equalsIgnoreCase(response)) {
                log.info("✅ [Redis] is active.");
            } else {
                log.warn("🚨 [Redis] responded unexpectedly: {}", response);
            }
        } catch (Exception e) {
            log.error("🔥 [Redis] connection failed: {}", e.getMessage());
        }
    }

    /**
     * AWS RDS (MySQL) 상태를 간단한 쿼리로 확인합니다.
     */
    private void checkRds() {
        try (java.sql.Connection connection = dataSource.getConnection();
             java.sql.Statement statement = connection.createStatement()) {
            statement.execute("SELECT 1");
            log.info("✅ [MySQL/RDS] is active.");
        } catch (Exception e) {
            log.error("🔥 [MySQL/RDS] connection failed: {}", e.getMessage());
        }
    }

    /**
     * MongoDB Atlas 상태를 ping 명령으로 확인합니다.
     */
    private void checkMongoDB() {
        try {
            // MongoDB의 ping 명령은 성공 시 { "ok" : 1 } 또는 { "ok" : 1.0 }을 반환합니다.
            Document pingResult = mongoTemplate.getDb().runCommand(new Document("ping", 1));

            // --- 수정된 부분 시작 ---
            // "ok" 필드의 값을 유연하게 처리하기 위해 Number 타입으로 받습니다.
            Number okStatus = pingResult.get("ok", Number.class);

            // Number 타입의 값을 double 값으로 변환하여 비교합니다.
            if (okStatus != null && okStatus.doubleValue() == 1.0) {
                // --- 수정된 부분 끝 ---
                log.info("✅ [MongoDB Atlas] is active.");
            } else {
                log.warn("🚨 [MongoDB Atlas] ping failed: {}", pingResult != null ? pingResult.toJson() : "null response");
            }
        } catch (Exception e) {
            log.error("🔥 [MongoDB Atlas] connection failed: {}", e.getMessage());
        }
    }

    /**
     * Elasticsearch 클러스터의 상태(health)를 확인합니다.
     */
    private void checkElasticsearch() {
        try {
            HealthResponse response = elasticsearchClient.cluster().health();
            HealthStatus status = response.status();

            if (status == HealthStatus.Red) {
                log.error("🔥 [Elasticsearch] cluster status is RED.");
            } else if (status == HealthStatus.Yellow) {
                log.warn("🚨 [Elasticsearch] cluster status is YELLOW.");
            } else {
                log.info("✅ [Elasticsearch] cluster status is GREEN.");
            }
        } catch (Exception e) {
            log.error("🔥 [Elasticsearch] connection failed: {}", e.getMessage());
        }
    }
}
