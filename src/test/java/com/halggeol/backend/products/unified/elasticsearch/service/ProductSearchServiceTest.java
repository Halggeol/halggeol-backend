//package com.halggeol.backend.products.unified.elasticsearch.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//import com.halggeol.backend.products.unified.elasticsearch.document.ProductDocument;
//import com.halggeol.backend.products.unified.elasticsearch.dto.ProductSearchResponseDTO;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//
//@ExtendWith(MockitoExtension.class)
//class ProductSearchServiceTest {
//
//    @Mock
//    private ElasticsearchOperations elasticsearchOperations;
//
//    @Mock
//    private SearchHits<ProductDocument> searchHits;
//
//    @Mock
//    private SearchHit<ProductDocument> searchHit1;
//
//    @Mock
//    private SearchHit<ProductDocument> searchHit2;
//
//    @InjectMocks
//    private ProductSearchService productSearchService;
//
//    // ProductDocument 인스턴스 준비
//    private ProductDocument testProductDoc1;
//    private ProductDocument testProductDoc2;
//
//    @BeforeEach
//    void setUp() {
//        // ProductDocument 인스턴스 생성 및 초기화
//        testProductDoc1 = new ProductDocument();
//        testProductDoc1.setId("D1");
//        testProductDoc1.setProductId("PROD001");
//        testProductDoc1.setName("Test Product 1");
//        testProductDoc1.setCompany("Test Company 1");
//        testProductDoc1.setTag1("tag1");
//        testProductDoc1.setTag2("tag2");
//        testProductDoc1.setTag3("tag3");
//        testProductDoc1.setTitle(3.5);
//        testProductDoc1.setSubTitle("Test SubTitle 1");
//        testProductDoc1.setType("savings");
//        testProductDoc1.setFSector(1);
//        testProductDoc1.setSaveTerm(12);
//        testProductDoc1.setMinAmount(10000);
//        testProductDoc1.setViewCnt(100);
//        testProductDoc1.setScrapCnt(50);
//        testProductDoc1.setTimestamp(LocalDateTime.parse("2023-01-01T10:00:00")); // LocalDateTime 타입으로 변경
//
//        testProductDoc2 = new ProductDocument();
//        testProductDoc2.setId("D2");
//        testProductDoc2.setProductId("PROD002");
//        testProductDoc2.setName("Test Product 2");
//        testProductDoc2.setCompany("Test Company 2");
//        testProductDoc2.setTag1("tag1");
//        testProductDoc2.setTag2("tag2");
//        testProductDoc2.setTag3("tag3");
//        testProductDoc2.setTitle(4.0); // Double 타입으로 변경
//        testProductDoc2.setSubTitle("Test SubTitle 2");
//        testProductDoc2.setType("deposit");
//        testProductDoc2.setFSector(2);
//        testProductDoc2.setSaveTerm(24);
//        testProductDoc2.setMinAmount(50000);
//        testProductDoc2.setViewCnt(200);
//        testProductDoc2.setScrapCnt(75);
//        testProductDoc2.setTimestamp(LocalDateTime.parse("2023-01-02T11:00:00")); // LocalDateTime 타입으로 변경
//    }
//
//    @Test
//    @DisplayName("모든 필터 없이 기본 검색 테스트")
//    void searchProducts_WithNoFilters_ShouldReturnAllResults() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHit2.getContent()).thenReturn(testProductDoc2);
//        when(searchHits.getSearchHits()).thenReturn(List.of(searchHit1, searchHit2));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, null, null, null, null, null
//        );
//
//        // Then
//        assertEquals(2, results.size());
//        assertEquals("Test Product 1", results.get(0).getName());
//        assertEquals("Test Product 2", results.get(1).getName());
//
//        verify(elasticsearchOperations, times(1)).search(any(NativeSearchQuery.class), eq(ProductDocument.class));
//    }
//
//    @Test
//    @DisplayName("키워드로 검색 테스트")
//    void searchProducts_WithKeyword_ShouldApplyMatchQuery() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, "Test Product", null, null, null, null
//        );
//
//        // Then
//        assertEquals(1, results.size());
//        assertEquals("Test Product 1", results.get(0).getName());
//
//        ArgumentCaptor<NativeSearchQuery> queryCaptor = ArgumentCaptor.forClass(NativeSearchQuery.class);
//        verify(elasticsearchOperations).search(queryCaptor.capture(), eq(ProductDocument.class));
//
//        NativeSearchQuery capturedQuery = queryCaptor.getValue();
//        assertNotNull(capturedQuery.getQuery());
//    }
//
//    @Test
//    @DisplayName("상품 유형 필터링 테스트")
//    void searchProducts_WithType_ShouldApplyTypeFilter() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, null, null, List.of("savings"), null, null
//        );
//
//        // Then
//        assertEquals(1, results.size());
//        assertEquals(Collections.singletonList("savings"), results.get(0).getType());
//    }
//
//    @Test
//    @DisplayName("금융권 필터링 테스트")
//    void searchProducts_WithFSector_ShouldApplyFSectorFilter() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, null, Collections.singletonList(1), null, null, null
//        );
//
//        // Then
//        assertEquals(1, results.size());
//        assertEquals(Collections.singletonList(1), results.get(0).getFSector());
//    }
//
//    @Test
//    @DisplayName("가입 기간 필터링 테스트")
//    void searchProducts_WithSaveTerm_ShouldApplySaveTermFilter() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, null, null, null, null, 12
//        );
//
//        // Then
//        assertEquals(1, results.size());
//        assertEquals(12, results.get(0).getSaveTerm());
//    }
//
//    @Test
//    @DisplayName("최소 가입금액 필터링 테스트")
//    void searchProducts_WithMinAmount_ShouldApplyMinAmountFilter() {
//        // Given
//        when(searchHit2.getContent()).thenReturn(testProductDoc2);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit2));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, null, null, null, "30000", null
//        );
//
//        // Then
//        assertEquals(1, results.size());
//        assertTrue(results.get(0).getMinAmount() >= 30000);
//    }
//
//    @Test
//    @DisplayName("잘못된 최소 가입금액으로 검색 시 필터 무시")
//    void searchProducts_WithInvalidMinAmount_ShouldIgnoreFilter() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHit2.getContent()).thenReturn(testProductDoc2);
//        when(searchHits.getSearchHits()).thenReturn(List.of(searchHit1, searchHit2));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, null, null, null, "invalid_amount", null
//        );
//
//        // Then
//        assertEquals(2, results.size());
//    }
//
//    @Test
//    @DisplayName("인기순 정렬(popularDesc) 테스트")
//    void searchProducts_WithPopularSort_ShouldApplyScriptSort() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        productSearchService.searchProducts(
//            "popularDesc", null, null, null, null, null
//        );
//
//        // Then
//        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ProductDocument.class));
//    }
//
//    @Test
//    @DisplayName("금리순 정렬(rateDesc) 테스트")
//    void searchProducts_WithRateSort_ShouldApplyTitleSort() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        productSearchService.searchProducts(
//            "rateDesc", null, null, null, null, null
//        );
//
//        // Then
//        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ProductDocument.class));
//    }
//
//    @Test
//    @DisplayName("복합 필터링 테스트")
//    void searchProducts_WithMultipleFilters_ShouldApplyAllFilters() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            "popularDesc", "Test", Collections.singletonList(1), List.of("savings"), "5000", 12
//        );
//
//        // Then
//        assertEquals(1, results.size());
//        ProductSearchResponseDTO result = results.get(0);
//        assertEquals("Test Product 1", result.getName());
//        assertEquals(Collections.singletonList("savings"), result.getType());
//        assertEquals(Collections.singletonList(1), result.getFSector());
//        assertEquals(12, result.getSaveTerm());
//        assertTrue(result.getMinAmount() >= 5000);
//    }
//
//    @Test
//    @DisplayName("검색 결과가 없는 경우 테스트")
//    void searchProducts_WithNoResults_ShouldReturnEmptyList() {
//        // Given
//        when(searchHits.getSearchHits()).thenReturn(Collections.emptyList());
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, "NonExistentProduct", null, null, null, null
//        );
//
//        // Then
//        assertTrue(results.isEmpty());
//    }
//
//    @Test
//    @DisplayName("DTO 변환 검증 테스트")
//    void searchProducts_ShouldCorrectlyConvertToDTO() {
//        // Given
//        when(searchHit1.getContent()).thenReturn(testProductDoc1);
//        when(searchHits.getSearchHits()).thenReturn(Collections.singletonList(searchHit1));
//        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ProductDocument.class)))
//            .thenReturn(searchHits);
//
//        // When
//        List<ProductSearchResponseDTO> results = productSearchService.searchProducts(
//            null, null, null, null, null, null
//        );
//
//        // Then
//        assertEquals(1, results.size());
//        ProductSearchResponseDTO result = results.get(0);
//
//        assertEquals(testProductDoc1.getProductId(), result.getProductId());
//        assertEquals(testProductDoc1.getName(), result.getName());
//        assertEquals(testProductDoc1.getCompany(), result.getCompany());
//        assertEquals(testProductDoc1.getTag1(), result.getTag1());
//        assertEquals(testProductDoc1.getTag2(), result.getTag2());
//        assertEquals(testProductDoc1.getTag3(), result.getTag3());
//        assertEquals(testProductDoc1.getTitle(), result.getTitle());
//        assertEquals(testProductDoc1.getSubTitle(), result.getSubTitle());
//        assertEquals(Collections.singletonList(testProductDoc1.getType()), result.getType());
//        assertEquals(Collections.singletonList(testProductDoc1.getFSector()), result.getFSector());
//        assertEquals(testProductDoc1.getSaveTerm(), result.getSaveTerm());
//        assertEquals(testProductDoc1.getMinAmount(), result.getMinAmount());
//        assertEquals(testProductDoc1.getViewCnt(), result.getViewCnt());
//        assertEquals(testProductDoc1.getScrapCnt(), result.getScrapCnt());
//    }
//}
