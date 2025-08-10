package com.halggeol.backend.products.unified.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.InlineScript;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.SortOrder;
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

    public void saveRecentSearch(String keyword, Integer userId){
        try{
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
    public List<RecentSearchResponseDTO> getRecentSearches(int size, Integer userId){
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
                .size(size)
                .sort(sort->sort.field(f->f.field("timestamp").order(SortOrder.Desc)))
            );

            SearchResponse<RecentSearchDocument> response = esClient.search(request, RecentSearchDocument.class);

            return response.hits().hits().stream()
                .map(Hit::source)
                .map(this::convertToRecentSearchDTO)
                .collect(Collectors.toList());
        } catch (Exception e){
            log.error("Failed to get recent searches", e);
            return List.of();
        }
    }

    // 인기 건색어 조회 (상위 5개)
    public List<PopularSearchResponseDTO> getPopularSearches(int size){
        try{
            SearchRequest request = SearchRequest.of(s->s
                .index("popular_searches_index")
                .size(size)
                .sort(sort->sort.field(f->f.field("count").order(SortOrder.Desc)))
            );

            SearchResponse<PopularSearchDocument> response = esClient.search(request, PopularSearchDocument.class);

            return response.hits().hits().stream()
                .map(Hit::source)
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
