package com.halggeol.backend.products.unified.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ShardStatistics;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.halggeol.backend.products.unified.elasticsearch.document.PopularSearchDocument;
import com.halggeol.backend.products.unified.elasticsearch.document.RecentSearchDocument;
import com.halggeol.backend.products.unified.elasticsearch.dto.PopularSearchResponseDTO;
import com.halggeol.backend.products.unified.elasticsearch.dto.RecentSearchResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // 불필요한 스터빙 오류를 완화
@DisplayName("SearchLogService 단위 테스트")
class SearchLogServiceTest {

    @Mock
    private ElasticsearchClient esClient;

    @InjectMocks
    private SearchLogService searchLogService;

    private Integer testUserId;
    private String testKeyword;

    @BeforeEach
    void setUp() {
        testUserId = 123;
        testKeyword = "테스트";
    }

    @Test
    @DisplayName("최근 검색어 저장: deleteByQuery(람다)와 index(객체)가 호출됨")
    void saveRecentSearch_Success() throws IOException {
        // Given
        // deleteByQuery는 람다(Function)를 받음
        when(esClient.deleteByQuery(any(Function.class))).thenReturn(mock(DeleteByQueryResponse.class));
        // index는 IndexRequest 객체를 받음
        when(esClient.index(any(IndexRequest.class))).thenReturn(mock(IndexResponse.class));

        // When
        searchLogService.saveRecentSearch(testKeyword, testUserId);

        // Then
        verify(esClient, times(1)).deleteByQuery(any(Function.class));
        verify(esClient, times(1)).index(any(IndexRequest.class));
    }

    @Test
    @DisplayName("최근 검색어 개별 삭제: deleteByQuery(람다)가 호출됨")
    void deleteRecentSearch_Success() throws IOException {
        // Given
        when(esClient.deleteByQuery(any(Function.class))).thenReturn(mock(DeleteByQueryResponse.class));

        // When
        searchLogService.deleteRecentSearch(testKeyword, testUserId);

        // Then
        verify(esClient, times(1)).deleteByQuery(any(Function.class));
    }

    @Test
    @DisplayName("최근 검색어 전체 삭제: deleteByQuery(람다)가 호출됨")
    void deleteAllRecentSearches_Success() throws IOException {
        // Given
        when(esClient.deleteByQuery(any(Function.class))).thenReturn(mock(DeleteByQueryResponse.class));

        // When
        searchLogService.deleteAllRecentSearches(testUserId);

        // Then
        verify(esClient, times(1)).deleteByQuery(any(Function.class));
    }

    @Test
    @DisplayName("최근 검색어 조회: 성공적으로 DTO 목록을 반환함")
    void getRecentSearches_Success() throws IOException {
        // Given
        RecentSearchDocument doc = RecentSearchDocument.builder().keyword(testKeyword).build();
        SearchResponse<RecentSearchDocument> mockResponse = createMockSearchResponse(List.of(doc));
        // search는 SearchRequest 객체를 받음
        when(esClient.search(any(SearchRequest.class), eq(RecentSearchDocument.class))).thenReturn(mockResponse);

        // When
        List<RecentSearchResponseDTO> results = searchLogService.getRecentSearches(testUserId);

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(testKeyword, results.get(0).getKeyword());
    }

    @Test
    @DisplayName("인기 검색어 카운트 증가: update(객체)가 호출됨")
    void incrementPopularSearchCount_Success() throws IOException {
        // Given
        when(esClient.update(any(UpdateRequest.class), eq(PopularSearchDocument.class)))
            .thenReturn(mock(UpdateResponse.class));

        // When
        searchLogService.incrementPopularSearchCount(testKeyword);

        // Then
        verify(esClient, times(1)).update(any(UpdateRequest.class), eq(PopularSearchDocument.class));
    }

    @Test
    @DisplayName("인기 검색어 조회: 성공적으로 DTO 목록을 반환함")
    void getPopularSearches_Success() throws IOException {
        // Given
        PopularSearchDocument doc = PopularSearchDocument.builder().keyword(testKeyword).count(100).build();
        SearchResponse<PopularSearchDocument> mockResponse = createMockSearchResponse(List.of(doc));
        when(esClient.search(any(SearchRequest.class), eq(PopularSearchDocument.class))).thenReturn(mockResponse);

        // When
        List<PopularSearchResponseDTO> results = searchLogService.getPopularSearches();

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(testKeyword, results.get(0).getKeyword());
        assertEquals(100, results.get(0).getCount());
    }

    // --- 헬퍼 메서드 (하나로 통합) ---

    private <T> SearchResponse<T> createMockSearchResponse(List<T> documents) {
        List<Hit<T>> hits = documents.stream()
            .map(doc -> Hit.<T>of(h -> h.index("any_index").id("any_id").source(doc)))
            .collect(Collectors.toList());

        TotalHits totalHits = TotalHits.of(th -> th.value(hits.size()).relation(TotalHitsRelation.Eq));
        HitsMetadata<T> hitsMetadata = HitsMetadata.of(hm -> hm.hits(hits).total(totalHits));
        ShardStatistics shards = ShardStatistics.of(s -> s.total(1).successful(1).failed(0));

        return SearchResponse.of(sr -> sr.hits(hitsMetadata).took(10).timedOut(false).shards(shards));
    }
}