package com.halggeol.backend.products.service;

import com.halggeol.backend.products.dto.*;
import com.halggeol.backend.products.mapper.ProductDetailMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductDetailService 테스트")
class ProductDetailServiceImplTest {

    @Mock
    private ProductDetailMapper<Object> productDetailMapper; // ProductDetailMapper 목 객체

    @InjectMocks
    private ProductDetailServiceImpl productDetailService; // 테스트 대상 서비스 (목 객체 주입)

    private String testUserId;

    @BeforeEach
    void setUp() {
        testUserId = "1";
    }

    // --- getProductDetailById 테스트 ---

    @Test
    @DisplayName("getProductDetailById - 예금 상품 조회 성공")
    void testGetProductDetailById_Deposit() {
        String productId = "D1001";
        DepositDetailResponseDTO expectedDto = new DepositDetailResponseDTO();

        when(productDetailMapper.selectDepositDetailById(productId, testUserId)).thenReturn(expectedDto);

        doNothing().when(productDetailMapper).incrementDepositViewCount(productId);


        Object result = productDetailService.getProductDetailById(productId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectDepositDetailById(productId, testUserId);
        verify(productDetailMapper, times(1)).incrementDepositViewCount(productId);
    }

    @Test
    @DisplayName("getProductDetailById - 적금 상품 조회 성공")
    void testGetProductDetailById_Savings() {
        String productId = "S2002";
        SavingsDetailResponseDTO expectedDto = new SavingsDetailResponseDTO();

        when(productDetailMapper.selectSavingsDetailById(productId, testUserId)).thenReturn(expectedDto);
        doNothing().when(productDetailMapper).incrementSavingsViewCount(productId);

        Object result = productDetailService.getProductDetailById(productId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectSavingsDetailById(productId, testUserId);
        verify(productDetailMapper, times(1)).incrementSavingsViewCount(productId);
    }

    @Test
    @DisplayName("getProductDetailById - 펀드 상품 조회 성공")
    void testGetProductDetailById_Fund() {
        String productId = "F3003";
        FundDetailResponseDTO expectedDto = new FundDetailResponseDTO();

        when(productDetailMapper.selectFundDetailById(productId, testUserId)).thenReturn(expectedDto);
        doNothing().when(productDetailMapper).incrementFundViewCount(productId);

        Object result = productDetailService.getProductDetailById(productId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectFundDetailById(productId, testUserId);
        verify(productDetailMapper, times(1)).incrementFundViewCount(productId);
    }

    @Test
    @DisplayName("getProductDetailById - 외환 상품 조회 성공")
    void testGetProductDetailById_Forex() {
        String productId = "X4004";
        ForexDetailResponseDTO expectedDto = new ForexDetailResponseDTO();

        when(productDetailMapper.selectForexDetailById(productId, testUserId)).thenReturn(expectedDto);
        doNothing().when(productDetailMapper).incrementForexViewCount(productId);

        Object result = productDetailService.getProductDetailById(productId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectForexDetailById(productId, testUserId);
        verify(productDetailMapper, times(1)).incrementForexViewCount(productId);
    }

    @Test
    @DisplayName("getProductDetailById - 공격형 연금 상품 조회 성공")
    void testGetProductDetailById_AggressivePension() {
        String productId = "A5005"; // 공격형 연금
        PensionDetailResponseDTO expectedDto = new PensionDetailResponseDTO();

        when(productDetailMapper.selectPensionDetailById(productId, testUserId)).thenReturn(expectedDto);
        doNothing().when(productDetailMapper).incrementPensionViewCount(productId);

        Object result = productDetailService.getProductDetailById(productId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectPensionDetailById(productId, testUserId);
        verify(productDetailMapper, times(1)).incrementPensionViewCount(productId);
    }

    @Test
    @DisplayName("getProductDetailById - 안정형 연금 상품 조회 성공")
    void testGetProductDetailById_ConservativePension() {
        String productId = "C6006"; // 안정형 연금
        PensionDetailResponseDTO expectedDto = new PensionDetailResponseDTO();

        when(productDetailMapper.selectPensionDetailById(productId, testUserId)).thenReturn(expectedDto);
        doNothing().when(productDetailMapper).incrementPensionViewCount(productId);

        Object result = productDetailService.getProductDetailById(productId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectPensionDetailById(productId, testUserId);
        verify(productDetailMapper, times(1)).incrementPensionViewCount(productId);
    }

    @Test
    @DisplayName("getProductDetailById - 유효하지 않은 상품 ID 접두사 예외 발생")
    void testGetProductDetailById_InvalidPrefix() {
        String productId = "Z7007";

        // IllegalArgumentException이 발생하는지 검증
        assertThrows(IllegalArgumentException.class, () -> {
            productDetailService.getProductDetailById(productId, testUserId);
        });

        // 예외 발생 시에는 매퍼 메서드가 호출되지 않았음을 검증
        verify(productDetailMapper, never()).selectDepositDetailById(anyString(), anyString());
        verify(productDetailMapper, never()).selectSavingsDetailById(anyString(), anyString());
        verify(productDetailMapper, never()).selectFundDetailById(anyString(), anyString());
        verify(productDetailMapper, never()).selectForexDetailById(anyString(), anyString());
        verify(productDetailMapper, never()).selectPensionDetailById(anyString(), anyString());
        verify(productDetailMapper, never()).incrementDepositViewCount(anyString());
        verify(productDetailMapper, never()).incrementSavingsViewCount(anyString());
        verify(productDetailMapper, never()).incrementFundViewCount(anyString());
        verify(productDetailMapper, never()).incrementForexViewCount(anyString());
        verify(productDetailMapper, never()).incrementPensionViewCount(anyString());
    }

    @Test
    @DisplayName("getProductDetailById - productId가 null일 경우 예외 발생")
    void testGetProductDetailById_NullProductId() {
        String productId = null; // null Product ID

        assertThrows(NullPointerException.class, () -> { // NullPointerException 또는 서비스 로직에 따라 IllegalArgumentException
            productDetailService.getProductDetailById(productId, testUserId);
        });

        // 예상대로 매퍼 메서드가 호출되지 않았음을 검증
        verifyNoInteractions(productDetailMapper); // Mock 객체에 대한 어떠한 상호작용도 없었음을 검증
    }

    @Test
    @DisplayName("getProductDetailById - productId가 빈 문자열일 경우 예외 발생")
    void testGetProductDetailById_EmptyProductId() {
        String productId = ""; // 빈 문자열 Product ID

        assertThrows(StringIndexOutOfBoundsException.class, () -> { // charAt(0) 때문에 StringIndexOutOfBoundsException 발생
            productDetailService.getProductDetailById(productId, testUserId);
        });

        // 예상대로 매퍼 메서드가 호출되지 않았음을 검증
        verifyNoInteractions(productDetailMapper); // Mock 객체에 대한 어떠한 상호작용도 없었음을 검증
    }


    // --- incrementProductViewCountAsync 테스트 ---

    @Test
    @DisplayName("incrementProductViewCountAsync - 예금 조회수 증가")
    void testIncrementProductViewCountAsync_Deposit() {
        String productId = "D1001";
        doNothing().when(productDetailMapper).incrementDepositViewCount(productId);

        productDetailService.incrementProductViewCountAsync(productId);

        verify(productDetailMapper, times(1)).incrementDepositViewCount(productId);
    }

    @Test
    @DisplayName("incrementProductViewCountAsync - 적금 조회수 증가")
    void testIncrementProductViewCountAsync_Savings() {
        String productId = "S2002";
        doNothing().when(productDetailMapper).incrementSavingsViewCount(productId);

        productDetailService.incrementProductViewCountAsync(productId);

        verify(productDetailMapper, times(1)).incrementSavingsViewCount(productId);
    }

    @Test
    @DisplayName("incrementProductViewCountAsync - 펀드 조회수 증가")
    void testIncrementProductViewCountAsync_Fund() {
        String productId = "F3003";
        doNothing().when(productDetailMapper).incrementFundViewCount(productId);

        productDetailService.incrementProductViewCountAsync(productId);

        verify(productDetailMapper, times(1)).incrementFundViewCount(productId);
    }

    @Test
    @DisplayName("incrementProductViewCountAsync - 외환 조회수 증가")
    void testIncrementProductViewCountAsync_Forex() {
        String productId = "X4004";
        doNothing().when(productDetailMapper).incrementForexViewCount(productId);

        productDetailService.incrementProductViewCountAsync(productId);

        verify(productDetailMapper, times(1)).incrementForexViewCount(productId);
    }

    @Test
    @DisplayName("incrementProductViewCountAsync - 공격형 연금 조회수 증가")
    void testIncrementProductViewCountAsync_AggressivePension() {
        String productId = "A5005";
        doNothing().when(productDetailMapper).incrementPensionViewCount(productId);

        productDetailService.incrementProductViewCountAsync(productId);

        verify(productDetailMapper, times(1)).incrementPensionViewCount(productId);
    }

    @Test
    @DisplayName("incrementProductViewCountAsync - 안정형 연금 조회수 증가")
    void testIncrementProductViewCountAsync_ConservativePension() {
        String productId = "C6006";
        doNothing().when(productDetailMapper).incrementPensionViewCount(productId);

        productDetailService.incrementProductViewCountAsync(productId);

        verify(productDetailMapper, times(1)).incrementPensionViewCount(productId);
    }
}