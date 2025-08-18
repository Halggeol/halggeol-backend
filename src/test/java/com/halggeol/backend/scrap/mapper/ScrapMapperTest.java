package com.halggeol.backend.scrap.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrappedProductResponseDTO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScrapMapper 테스트")
class ScrapMapperTest {

    @Mock
    private ScrapMapper scrapMapper; // 테스트 대상 Mapper를 Mock 객체로 생성

    // 공통 userId만 BeforeEach에서 설정
    private int testUserId;

    @BeforeEach
    void setUp() {
        testUserId = 1;
    }

    // --- Scrap CRUD Tests ---

    @Test
    @DisplayName("사용자 스크랩 추가: 성공")
    void testInsertUserScrap() {
        // Given
        Scrap scrap = new Scrap();
        scrap.setUserId(testUserId);
        scrap.setProductId("D123"); // 예시 상품 ID

        // When (void 메서드이므로 doNothing 사용)
        doNothing().when(scrapMapper).insertUserScrap(scrap);
        scrapMapper.insertUserScrap(scrap);

        // Then
        // insertUserScrap 메서드가 정확히 1번 호출되었는지 검증
        verify(scrapMapper, times(1)).insertUserScrap(scrap);
    }

    @Test
    @DisplayName("사용자 스크랩 삭제: 성공")
    void testDeleteUserScrap() {
        // Given
        String productId = "F456"; // 예시 상품 ID

        // When (void 메서드이므로 doNothing 사용)
        doNothing().when(scrapMapper).deleteUserScrap(testUserId, productId);
        scrapMapper.deleteUserScrap(testUserId, productId);

        // Then
        // deleteUserScrap 메서드가 정확히 1번 호출되었는지 검증
        verify(scrapMapper, times(1)).deleteUserScrap(testUserId, productId);
    }

    // --- 관심수 증가 테스트 ---

    @Test
    @DisplayName("예금 상품 관심수 증가: 성공")
    void testIncrementDepositScrapCount() {
        // Given
        String depositProductId = "D789";

        // When (void 메서드이므로 doNothing 사용)
        doNothing().when(scrapMapper).incrementDepositScrapCount(depositProductId);
        scrapMapper.incrementDepositScrapCount(depositProductId);

        // Then
        verify(scrapMapper, times(1)).incrementDepositScrapCount(depositProductId);
    }

    @Test
    @DisplayName("적금 상품 관심수 증가: 성공")
    void testIncrementSavingsScrapCount() {
        // Given
        String savingsProductId = "S012";

        // When
        doNothing().when(scrapMapper).incrementSavingsScrapCount(savingsProductId);
        scrapMapper.incrementSavingsScrapCount(savingsProductId);

        // Then
        verify(scrapMapper, times(1)).incrementSavingsScrapCount(savingsProductId);
    }

    @Test
    @DisplayName("펀드 상품 관심수 증가: 성공")
    void testIncrementFundScrapCount() {
        // Given
        String fundProductId = "F345";

        // When
        doNothing().when(scrapMapper).incrementFundScrapCount(fundProductId);
        scrapMapper.incrementFundScrapCount(fundProductId);

        // Then
        verify(scrapMapper, times(1)).incrementFundScrapCount(fundProductId);
    }

    @Test
    @DisplayName("외환 상품 관심수 증가: 성공")
    void testIncrementForexScrapCount() {
        // Given
        String forexProductId = "X678";

        // When
        doNothing().when(scrapMapper).incrementForexScrapCount(forexProductId);
        scrapMapper.incrementForexScrapCount(forexProductId);

        // Then
        verify(scrapMapper, times(1)).incrementForexScrapCount(forexProductId);
    }

    @Test
    @DisplayName("연금 상품 관심수 증가: 성공")
    void testIncrementPensionScrapCount() {
        // Given
        String pensionProductId = "A901"; // 연금 상품 ID (A 또는 C)

        // When
        doNothing().when(scrapMapper).incrementPensionScrapCount(pensionProductId);
        scrapMapper.incrementPensionScrapCount(pensionProductId);

        // Then
        verify(scrapMapper, times(1)).incrementPensionScrapCount(pensionProductId);
    }

    // --- 관심수 감소 테스트 ---

    @Test
    @DisplayName("예금 상품 관심수 감소: 성공")
    void testDecrementDepositScrapCount() {
        // Given
        String depositProductId = "D234";

        // When
        doNothing().when(scrapMapper).decrementDepositScrapCount(depositProductId);
        scrapMapper.decrementDepositScrapCount(depositProductId);

        // Then
        verify(scrapMapper, times(1)).decrementDepositScrapCount(depositProductId);
    }

    @Test
    @DisplayName("적금 상품 관심수 감소: 성공")
    void testDecrementSavingsScrapCount() {
        // Given
        String savingsProductId = "S567";

        // When
        doNothing().when(scrapMapper).decrementSavingsScrapCount(savingsProductId);
        scrapMapper.decrementSavingsScrapCount(savingsProductId);

        // Then
        verify(scrapMapper, times(1)).decrementSavingsScrapCount(savingsProductId);
    }

    @Test
    @DisplayName("펀드 상품 관심수 감소: 성공")
    void testDecrementFundScrapCount() {
        // Given
        String fundProductId = "F890";

        // When
        doNothing().when(scrapMapper).decrementFundScrapCount(fundProductId);
        scrapMapper.decrementFundScrapCount(fundProductId);

        // Then
        verify(scrapMapper, times(1)).decrementFundScrapCount(fundProductId);
    }

    @Test
    @DisplayName("외환 상품 관심수 감소: 성공")
    void testDecrementForexScrapCount() {
        // Given
        String forexProductId = "X123";

        // When
        doNothing().when(scrapMapper).decrementForexScrapCount(forexProductId);
        scrapMapper.decrementForexScrapCount(forexProductId);

        // Then
        verify(scrapMapper, times(1)).decrementForexScrapCount(forexProductId);
    }

    @Test
    @DisplayName("연금 상품 관심수 감소: 성공")
    void testDecrementPensionScrapCount() {
        // Given
        String pensionProductId = "C456"; // 연금 상품 ID (A 또는 C)

        // When
        doNothing().when(scrapMapper).decrementPensionScrapCount(pensionProductId);
        scrapMapper.decrementPensionScrapCount(pensionProductId);

        // Then
        verify(scrapMapper, times(1)).decrementPensionScrapCount(pensionProductId);
    }

    @Test
    @DisplayName("관심상품 조회: 성공")
    void testSelectScrappedProducts() {
        // Given
        List<String> testTypes = List.of("savings", "pension");
        String testSort = "rateDesc";

        ScrappedProductResponseDTO mockProduct1 = new ScrappedProductResponseDTO();
        mockProduct1.setProductId("S001");
        mockProduct1.setName("savings");

        ScrappedProductResponseDTO mockProduct2 = new ScrappedProductResponseDTO();
        mockProduct2.setProductId("P002");
        mockProduct2.setName("pension");

        List<ScrappedProductResponseDTO> mockProducts = List.of(mockProduct1, mockProduct2);

        // When
        Mockito.when(scrapMapper.selectScrappedProducts(testUserId, testTypes, testSort)).thenReturn(mockProducts);

        List<ScrappedProductResponseDTO> result = scrapMapper.selectScrappedProducts(testUserId, testTypes, testSort);

        verify(scrapMapper, times(1)).selectScrappedProducts(testUserId, testTypes, testSort);
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals("S001", result.get(0).getProductId());
        assertEquals("P002", result.get(1).getProductId());


    }
}
