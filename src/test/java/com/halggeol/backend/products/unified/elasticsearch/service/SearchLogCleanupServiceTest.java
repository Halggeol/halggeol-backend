package com.halggeol.backend.products.unified.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SearchLogCleanupService 단위 테스트")
class SearchLogCleanupServiceTest {

    @Mock // 의존성인 ElasticsearchClient를 가짜(Mock) 객체로 생성
    private ElasticsearchClient esClient;

    @InjectMocks // @Mock으로 만든 가짜 객체를 테스트 대상 서비스에 주입
    private SearchLogCleanupService searchLogCleanupService;

    @Test
    @DisplayName("오래된 최근 검색어 삭제: 성공적으로 deleteByQuery를 호출함")
    void deleteOldRecentSearches_Success() throws IOException {
        // Given
        // esClient.deleteByQuery가 호출될 때, 가짜 응답을 반환하도록 설정
        when(esClient.deleteByQuery(any(Function.class)))
            .thenReturn(mock(DeleteByQueryResponse.class));

        // When
        // @Scheduled가 아닌, 테스트에서는 메서드를 직접 호출
        searchLogCleanupService.deleteOldRecentSearches();

        // Then
        // esClient.deleteByQuery 메서드가 정확히 1번 호출되었는지 검증
        // 람다 함수를 인자로 받으므로 any(Function.class)로 확인
        verify(esClient, times(1)).deleteByQuery(any(Function.class));
    }

    @Test
    @DisplayName("오래된 최근 검색어 삭제: ES 클라이언트에서 예외 발생 시 서비스가 중단되지 않음")
    void deleteOldRecentSearches_WhenEsClientThrowsException_ShouldNotThrow() throws IOException {
        // Given
        // esClient.deleteByQuery가 호출될 때, IOException을 던지도록 설정
        when(esClient.deleteByQuery(any(Function.class)))
            .thenThrow(new IOException("Elasticsearch connection failed"));

        // When & Then
        // 서비스 메서드 내부의 try-catch 블록이 예외를 처리하므로,
        // searchLogCleanupService.deleteOldRecentSearches() 호출 시 예외가 밖으로 던져지지 않음을 확인
        assertDoesNotThrow(() -> searchLogCleanupService.deleteOldRecentSearches());

        // 예외가 발생했더라도 메서드는 1번 호출되어야 함
        verify(esClient, times(1)).deleteByQuery(any(Function.class));
    }
}