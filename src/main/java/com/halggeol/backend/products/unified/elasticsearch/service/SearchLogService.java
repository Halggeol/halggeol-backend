package com.halggeol.backend.products.unified.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.InlineScript;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.halggeol.backend.products.unified.elasticsearch.document.PopularSearchDocument;
import com.halggeol.backend.products.unified.elasticsearch.document.RecentSearchDocument;
import com.halggeol.backend.products.unified.elasticsearch.dto.PopularSearchResponseDTO;
import com.halggeol.backend.products.unified.elasticsearch.dto.RecentSearchResponseDTO;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchLogService {
    private final ElasticsearchClient esClient;
    private static final int MAX_SEARCHES = 5; // 최근 및 인기검색어 보여줄 개수

    // 최근 검색어 저장
    public void saveRecentSearch(String keyword, Integer userId){
        if (userId == null || userId <= 0) {
            log.warn("Skipping saveRecentSearch: invalid userId ({}) for keyword '{}'", userId, keyword);
            return;
        }
        try{
            // 기존 동일 검색어 기록 먼저 삭제 (최근 검색어 중복 제거)
            esClient.deleteByQuery(d->d
                .index("recent_searches_index")
                .query(q->q
                    .bool(b->b
                        .must(m->m
                            .term(t->t.field("userId").value(userId))
                        )
                        .must(m->m
                            .term(t->t.field("keyword").value(keyword))
                        )
                    )
                )
                .refresh(true)
            );

            // 새 검색어 저장
            // 삭제 후 새로 추가하므로, 이 기록은 항상 가장 최신이 됨
            RecentSearchDocument doc = RecentSearchDocument.builder()
                .keyword(keyword)
                .timestamp(Instant.now())
                .userId(userId)
                .build();

            esClient.index(IndexRequest.of(r->r
                .index("recent_searches_index")
                .document(doc)
                ));
        } catch (Exception e){
            log.error("Failed to save recent search", e);
        }
    }

    // 최근 검색어 개별 삭제
    public void deleteRecentSearch(String keyword, Integer userId){
        try {
            DeleteByQueryResponse response = esClient.deleteByQuery(d->d
                .index("recent_searches_index")
                .query(q->q
                    .bool(b->b
                        .must(m->m.term(t->t.field("userId").value(userId)))
                        .must(m->m.term(t->t.field("keyword").value(keyword)))
                    )
                )
                .refresh(true)
            );
            log.info("[DeleteByQuery] userId = {}, keyword = '{}', deletedCount = {}", userId, keyword, response.deleted());
        } catch (Exception e){
            log.error("Failed to delete recent search for keyword '{}'", keyword, e);
        }
    }

    // 최근 검색어 전체 삭제
    public void deleteAllRecentSearches(Integer userId) {
        try {
            DeleteByQueryResponse response = esClient.deleteByQuery(d->d
                .index("recent_searches_index")
                .query(q->q
                    .term(t->t.field("userId").value(userId))
                )
                .refresh(true)
            );
            log.info("[DeleteByQuery] userId={}, all recent searches deletedCount={}", userId, response.deleted());
        } catch (Exception e){
            log.error("Failed to delete all recent searches", e);
        }
    }

    public void incrementPopularSearchCount(String keyword) {
        try {
            PopularSearchDocument doc = PopularSearchDocument.builder()
                .keyword(keyword)
                .count(1)
                .lastSearchTime(Instant.now())
                .build();

            // count 증가: doc 없으면 생성
            esClient.update(UpdateRequest.of(r -> r
                .index("popular_searches_index")
                .id(keyword) // keyword를 ID로 사용
                .script(Script.of(s -> s
                    .inline(InlineScript.of(i -> i
                        .source("ctx._source.count += 1; ctx._source.lastSearchTime = params.now")
                        .params(Map.of("now", JsonData.of(Instant.now().toString())))
                        ))
                    ))
                    .upsert(doc)
            ), PopularSearchDocument.class);
        } catch (Exception e) {
            log.error("Failed to increment popular search count", e);
        }
    }

    // 최근 검색어 조회 (최근 5개)
    public List<RecentSearchResponseDTO> getRecentSearches(Integer userId){
        try{
            SearchRequest request = SearchRequest.of(s->s
                .index("recent_searches_index")
                .query(q->q
                    .bool(b->b
                        .filter(f->f
                            .term(t->t
                                .field("userId")
                                .value(userId)
                            )
                        )
                    )
                )
                .size(MAX_SEARCHES)
                .sort(sort->sort.field(f->f.field("timestamp").order(SortOrder.Desc)))
            );

            SearchResponse<RecentSearchDocument> response = esClient.search(request, RecentSearchDocument.class);

            return response.hits().hits().stream()
                .map(Hit::source)
                .filter(doc -> doc !=null && doc.getKeyword() !=null && !doc.getKeyword().isBlank())
                .map(this::convertToRecentSearchDTO)
                .collect(Collectors.toList());
        } catch (Exception e){
            log.error("Failed to get recent searches", e);
            return List.of();
        }
    }

    // 인기 검색어 조회 (상위 5개)
    public List<PopularSearchResponseDTO> getPopularSearches(){
        try{
            SearchRequest request = SearchRequest.of(s->s
                .index("popular_searches_index")
                .size(MAX_SEARCHES)
                .sort(sort->sort.field(f->f.field("count").order(SortOrder.Desc)))
            );

            SearchResponse<PopularSearchDocument> response = esClient.search(request, PopularSearchDocument.class);

            return response.hits().hits().stream()
                .map(Hit::source)
                .filter(doc -> doc != null && doc.getKeyword() != null && !doc.getKeyword().isBlank())
                .map(this::convertToPopularSearchDTO)
                .collect(Collectors.toList());
        } catch (Exception e){
            log.error("Failed to get popular searches", e);
            return List.of();
        }
    }

    // document -> DTO 변환
    private RecentSearchResponseDTO convertToRecentSearchDTO(RecentSearchDocument doc){
        return RecentSearchResponseDTO.builder()
            .keyword(doc.getKeyword())
            .timestamp(doc.getTimestamp())
            .userId(doc.getUserId())
            .build();
    }

    private PopularSearchResponseDTO convertToPopularSearchDTO(PopularSearchDocument doc){
        return PopularSearchResponseDTO.builder()
            .keyword(doc.getKeyword())
            .count(doc.getCount())
            .lastSearchTime(doc.getLastSearchTime())
            .build();
    }
}
