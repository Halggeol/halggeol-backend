package com.halggeol.backend.products.unified.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ShardStatistics;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.halggeol.backend.products.unified.elasticsearch.document.ProductDocument;
import com.halggeol.backend.products.unified.elasticsearch.dto.ProductSearchResponseDTO;
import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductSearchService 단위 테스트")
class ProductSearchServiceTest {

    @Mock
    private ElasticsearchClient esClient;

    @Mock
    private SearchLogService searchLogService;

    @InjectMocks
    private ProductSearchService productSearchService;

    private ProductDocument testProductDoc1;
    private CustomUser testUser;

    @BeforeEach
    void setUp() {
        // 테스트용 ProductDocument 객체 생성
        testProductDoc1 = new ProductDocument();
        testProductDoc1.setProductId("PROD001");
        testProductDoc1.setName("테스트 예금 상품");
        testProductDoc1.setCompany("테스트 은행");
        testProductDoc1.setType("deposit");
        testProductDoc1.setFSector(1);
        testProductDoc1.setSaveTerm(12);
        testProductDoc1.setMinAmount(10000);
        testProductDoc1.setMinSaveTerm(6);
        testProductDoc1.setMaxSaveTerm(24);

        // 테스트용 사용자 객체 생성
        User user = User.builder()
            .id(1)
            .name("테스트 사용자")
            .email("kim01@example.com")
            .password("12341234")
            .build();
        testUser = new CustomUser(user);
    }

    // Mock SearchResponse를 생성하는 헬퍼 메서드
    private SearchResponse<ProductDocument> createMockSearchResponse(List<ProductDocument> documents) {
        List<Hit<ProductDocument>> hits = documents.stream()
            .map(doc->Hit.<ProductDocument>of(h->h
                .index("products_index")
                .id(doc.getProductId())
                .source(doc)
            ))
            .collect(Collectors.toList());

        TotalHits totalHits = TotalHits.of(th -> th.value(hits.size()).relation(TotalHitsRelation.Eq));
        HitsMetadata<ProductDocument> hitsMetadata = HitsMetadata.of(hm -> hm.hits(hits).total(totalHits));
        ShardStatistics shards = ShardStatistics.of(s->s.total(1).successful(1).failed(0));

        return SearchResponse.of(sr -> sr
            .hits(hitsMetadata)
            .took(10)
            .timedOut(false)
            .shards(shards)
        );
    }

    @Test
    @DisplayName("키워드 검색 시 match 쿼리 생성 및 로그 서비스 호출 검증")
    void searchProducts_WithKeyword_ShouldBuildCorrectQueryAndLog() throws IOException {
        // Given
        String keyword = "테스트";
        SearchResponse<ProductDocument> mockResponse = createMockSearchResponse(List.of(testProductDoc1));
        when(esClient.search(any(SearchRequest.class), eq(ProductDocument.class))).thenReturn(mockResponse);

        // When
        productSearchService.searchProducts(null, keyword, null, null, null, null, testUser);

        // Then
        // 1. esClient.search 메서드로 전달된 SearchRequest를 캡처
        ArgumentCaptor<SearchRequest> requestCaptor = ArgumentCaptor.forClass(SearchRequest.class);
        verify(esClient).search(requestCaptor.capture(), eq(ProductDocument.class));
        SearchRequest capturedRequest = requestCaptor.getValue();

        // 2. 캡처된 쿼리가 올바른지 검증
        Query query = capturedRequest.query();
        assertTrue(query.isBool());
        assertTrue(query.bool().must().get(0).bool().should().get(0).isMatch());
        assertEquals(keyword, query.bool().must().get(0).bool().should().get(0).match().query().stringValue());

        // 3. 로그 서비스가 호출되었는지 검증
        verify(searchLogService, times(1)).saveRecentSearch(keyword, testUser.getUser().getId());
        verify(searchLogService, times(1)).incrementPopularSearchCount(keyword);
    }

    @Test
    @DisplayName("다양한 필터 적용 시 올바른 filter 쿼리 생성 검증")
    void searchProducts_WithMultipleFilters_ShouldBuildCorrectFilterQuery() throws IOException {
        // Given
        List<String> types = List.of("deposit");
        List<Integer> fSectors = List.of(1);
        Integer minAmount = 10000;
        Integer saveTerm = 12;

        SearchResponse<ProductDocument> mockResponse = createMockSearchResponse(Collections.emptyList());
        when(esClient.search(any(SearchRequest.class), eq(ProductDocument.class))).thenReturn(mockResponse);

        // When
        productSearchService.searchProducts(null, null, fSectors, types, minAmount, saveTerm, null);

        // Then
        ArgumentCaptor<SearchRequest> requestCaptor = ArgumentCaptor.forClass(SearchRequest.class);
        verify(esClient).search(requestCaptor.capture(), eq(ProductDocument.class));
        SearchRequest capturedRequest = requestCaptor.getValue();

        // 캡처된 쿼리에서 필터 목록을 가져와 검증
        List<Query> filters = capturedRequest.query().bool().filter();
        assertEquals(4, filters.size()); // type, fSector, minAmount, saveTerm 4개의 필터가 적용되어야 함

        // 각 필터의 종류와 값이 올바른지 간단히 확인
        assertTrue(filters.stream().anyMatch(q -> q.isTerms() && q.terms().field().equals("type.keyword")));
        assertTrue(filters.stream().anyMatch(q -> q.isTerms() && q.terms().field().equals("fsector")));
        assertTrue(filters.stream().anyMatch(q -> q.isRange() && q.range().field().equals("minamount")));
        assertTrue(filters.stream().anyMatch(q -> q.isBool() && q.bool().should().get(0).isTerm())); // saveTerm 필터는 복잡한 bool 쿼리
    }

    @Test
    @DisplayName("금리순 정렬(rateDesc) 시 올바른 SortOptions 생성 검증")
    void searchProducts_WithRateSort_ShouldBuildCorrectSortOptions() throws IOException {
        // Given
        String sort = "rateDesc";
        SearchResponse<ProductDocument> mockResponse = createMockSearchResponse(Collections.emptyList());
        when(esClient.search(any(SearchRequest.class), eq(ProductDocument.class))).thenReturn(mockResponse);

        // When
        productSearchService.searchProducts(sort, null, null, null, null, null, null);

        // Then
        ArgumentCaptor<SearchRequest> requestCaptor = ArgumentCaptor.forClass(SearchRequest.class);
        verify(esClient).search(requestCaptor.capture(), eq(ProductDocument.class));
        SearchRequest capturedRequest = requestCaptor.getValue();

        // 정렬 옵션이 title 필드 기준 내림차순인지 검증
        assertEquals("title", capturedRequest.sort().get(0).field().field());
        assertEquals("desc", capturedRequest.sort().get(0).field().order().jsonValue());
    }

    @Test
    @DisplayName("기본(인기순) 정렬 시 올바른 Script Sort 생성 검증")
    void searchProducts_WithDefaultSort_ShouldBuildScriptSort() throws IOException {
        // Given
        String sort = null; // 기본 정렬
        SearchResponse<ProductDocument> mockResponse = createMockSearchResponse(Collections.emptyList());
        when(esClient.search(any(SearchRequest.class), eq(ProductDocument.class))).thenReturn(mockResponse);

        // When
        productSearchService.searchProducts(sort, null, null, null, null, null, null);

        // Then
        ArgumentCaptor<SearchRequest> requestCaptor = ArgumentCaptor.forClass(SearchRequest.class);
        verify(esClient).search(requestCaptor.capture(), eq(ProductDocument.class));
        SearchRequest capturedRequest = requestCaptor.getValue();

        // 정렬 옵션이 스크립트 기반인지 검증
        assertTrue(capturedRequest.sort().get(0).isScript());
        assertEquals("doc['view_cnt'].value + doc['scrap_cnt'].value * 2", capturedRequest.sort().get(0).script().script().inline().source());
    }

    @Test
    @DisplayName("검색 결과가 성공적으로 DTO로 변환되는지 검증")
    void searchProducts_Success_ShouldReturnCorrectDtoList() throws IOException {
        // Given
        SearchResponse<ProductDocument> mockResponse = createMockSearchResponse(List.of(testProductDoc1));
        when(esClient.search(any(SearchRequest.class), eq(ProductDocument.class))).thenReturn(mockResponse);

        // When
        ResponseEntity<?> responseEntity = productSearchService.searchProducts(null, null, null, null, null, null, null);
        List<ProductSearchResponseDTO> results = (List<ProductSearchResponseDTO>) responseEntity.getBody();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("PROD001", results.get(0).getProductId());
        assertEquals("테스트 예금 상품", results.get(0).getName());
    }

    @Test
    @DisplayName("Elasticsearch에서 예외 발생 시 500 에러 응답 반환")
    void searchProducts_WhenEsClientThrowsException_ShouldReturn500Error() throws IOException {
        // Given
        when(esClient.search(any(SearchRequest.class), eq(ProductDocument.class)))
            .thenThrow(new IOException("Elasticsearch connection failed"));

        // When
        ResponseEntity<?> responseEntity = productSearchService.searchProducts(null, "test", null, null, null, null, null);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(((String) responseEntity.getBody()).contains("검색 실패"));
    }
}