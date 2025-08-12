package com.halggeol.backend.products.unified.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonData;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchLogCleanupService {

    private final ElasticsearchClient esClient;
    private static final int RETENTION_DAYS = 30;

    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시, 30일 전 데이터 삭제
    public void deleteOldRecentSearches(){
        try{
            Instant cutoff = Instant.now().minus(RETENTION_DAYS, ChronoUnit.DAYS);

            esClient.deleteByQuery(d->d
                .index("recent_searches_index")
                .query(q->q
                    .range(r->r
                        .field("timestamp")
                        .lt(JsonData.of(cutoff.toString()))
                    )
                )
            );
            log.info("Old recent searches before {} deleted.", cutoff);
        } catch (Exception e){
            log.error("Delete old recent searches failed.", e);
        }
    }

}
