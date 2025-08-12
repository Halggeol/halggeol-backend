package com.halggeol.backend.common.service;

import com.halggeol.backend.common.HealthStatusHolder;
import com.halggeol.backend.common.enums.DBType;
import lombok.RequiredArgsConstructor;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.elasticsearch._types.HealthStatus;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
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

    private final HealthStatusHolder healthStatusHolder;

    private final RedisTemplate<String, Object> redisTemplate;
    private final MongoTemplate mongoTemplate;
    private final ElasticsearchClient elasticsearchClient;
    private final DataSource dataSource;

    @Scheduled(fixedRate = 3600000) // 5분 (300000), 1시간 (3600000)
    public void checkAllExternalServices() {
        log.info("--- Starting external services heartbeat check ---");

        checkRedis();
        checkRds();
        checkMongoDB();
        checkElasticsearch();

        log.info("--- Finished external services heartbeat check ---");
    }

    /**
     * Redis 서버 상태
     */
    private void checkRedis() {
        try {
            String response = redisTemplate.execute((RedisConnection connection) -> connection.ping());
            if ("PONG".equalsIgnoreCase(response)) {
                log.info("✅ [Redis] is active.");
                healthStatusHolder.updateStatusOnSuccess(DBType.REDIS);
            } else {
                log.warn("🚨 [Redis] responded unexpectedly: {}", response);
                healthStatusHolder.updateStatusOnFailure(DBType.REDIS, "Unexpected response: " + response);
            }
        } catch (Exception e) {
            log.error("🔥 [Redis] connection failed: {}", e.getMessage());
            healthStatusHolder.updateStatusOnFailure(DBType.REDIS, e.getMessage());
        }
    }

    /**
     * AWS RDS (MySQL) 상태
     */
    private void checkRds() {
        try (java.sql.Connection connection = dataSource.getConnection();
             java.sql.Statement statement = connection.createStatement()) {
            statement.execute("SELECT 1");
            log.info("✅ [MySQL/RDS] is active.");
            healthStatusHolder.updateStatusOnSuccess(DBType.MYSQL);
        } catch (Exception e) {
            log.error("🔥 [MySQL/RDS] connection failed: {}", e.getMessage());
            healthStatusHolder.updateStatusOnFailure(DBType.MYSQL, e.getMessage());
        }
    }

    /**
     * MongoDB Atlas 상태
     */
    private void checkMongoDB() {
        try {
            Document pingResult = mongoTemplate.getDb().runCommand(new Document("ping", 1));

            Number okStatus = pingResult.get("ok", Number.class);

            if (okStatus != null && okStatus.doubleValue() == 1.0) {
                log.info("✅ [MongoDB Atlas] is active.");
                healthStatusHolder.updateStatusOnSuccess(DBType.MONGODB);
            } else {
                log.warn("🚨 [MongoDB Atlas] ping failed: {}", pingResult != null ? pingResult.toJson() : "null response");
//                healthStatusHolder.updateStatusOnFailure("MongoDB Atlas", "Ping failed: " + (pingResult != null ? pingResult.toJson() : "null response"));

            }
        } catch (Exception e) {
            log.error("🔥 [MongoDB Atlas] connection failed: {}", e.getMessage());
            healthStatusHolder.updateStatusOnFailure(DBType.MONGODB, e.getMessage());
        }
    }

    /**
     * Elasticsearch 클러스터의 상태
     */
    private void checkElasticsearch() {
        try {
            HealthResponse response = elasticsearchClient.cluster().health();
            HealthStatus status = response.status();

            if (status == HealthStatus.Red) {
                log.error("🔥 [Elasticsearch] cluster status is RED.");
                healthStatusHolder.updateStatusOnFailure(DBType.ELASTICSEARCH, "Cluster status is RED");
            } else if (status == HealthStatus.Yellow) {
                log.warn("🚨 [Elasticsearch] cluster status is YELLOW.");


            } else {
                log.info("✅ [Elasticsearch] cluster status is GREEN.");
                healthStatusHolder.updateStatusOnSuccess(DBType.ELASTICSEARCH);
            }
        } catch (Exception e) {
            log.error("🔥 [Elasticsearch] connection failed: {}", e.getMessage());
            healthStatusHolder.updateStatusOnFailure(DBType.ELASTICSEARCH, e.getMessage());
        }
    }
}
