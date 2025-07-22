package com.halggeol.backend.products.mapper;

import com.halggeol.backend.products.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductDetailMapper 테스트")
class ProductDetailMapperTest {

    @Mock
    private ProductDetailMapper<Object> productDetailMapper;

    // 공통 userId만 BeforeEach에서 설정
    private String testUserId;

    @BeforeEach
    void setUp() {
        testUserId = "1";
    }

    // --- Detail Select Tests ---

    @Test
    @DisplayName("예금 상세 조회: 성공")
    void testSelectDepositDetailById() {
        String depositProductId = "D1"; // 예금 상품 ID
        DepositDetailResponseDTO expectedDto = new DepositDetailResponseDTO();
        when(productDetailMapper.selectDepositDetailById(depositProductId, testUserId)).thenReturn(expectedDto);

        DepositDetailResponseDTO result = productDetailMapper.selectDepositDetailById(depositProductId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectDepositDetailById(depositProductId, testUserId);
    }

    @Test
    @DisplayName("적금 상세 조회: 성공")
    void testSelectSavingsDetailById() {
        String savingsProductId = "S2"; // 적금 상품 ID
        SavingsDetailResponseDTO expectedDto = new SavingsDetailResponseDTO();
        when(productDetailMapper.selectSavingsDetailById(savingsProductId, testUserId)).thenReturn(expectedDto);

        SavingsDetailResponseDTO result = productDetailMapper.selectSavingsDetailById(savingsProductId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectSavingsDetailById(savingsProductId, testUserId);
    }

    @Test
    @DisplayName("펀드 상세 조회: 성공")
    void testSelectFundDetailById() {
        String fundProductId = "F3"; // 펀드 상품 ID
        FundDetailResponseDTO expectedDto = new FundDetailResponseDTO();
        when(productDetailMapper.selectFundDetailById(fundProductId, testUserId)).thenReturn(expectedDto);

        FundDetailResponseDTO result = productDetailMapper.selectFundDetailById(fundProductId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectFundDetailById(fundProductId, testUserId);
    }

    @Test
    @DisplayName("외환 상세 조회: 성공")
    void testSelectForexDetailById() {
        String forexProductId = "X4"; // 외환 상품 ID
        ForexDetailResponseDTO expectedDto = new ForexDetailResponseDTO();
        when(productDetailMapper.selectForexDetailById(forexProductId, testUserId)).thenReturn(expectedDto);

        ForexDetailResponseDTO result = productDetailMapper.selectForexDetailById(forexProductId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectForexDetailById(forexProductId, testUserId);
    }

    @Test
    @DisplayName("연금 상세 조회: 성공")
    void testSelectPensionDetailById() {

        String pensionProductId = "A5"; // 공격형 연금 상품 ID (예시)
        PensionDetailResponseDTO expectedDto = new PensionDetailResponseDTO();
        when(productDetailMapper.selectPensionDetailById(pensionProductId, testUserId)).thenReturn(expectedDto);

        PensionDetailResponseDTO result = productDetailMapper.selectPensionDetailById(pensionProductId, testUserId);

        assertThat(result).isEqualTo(expectedDto);
        verify(productDetailMapper, times(1)).selectPensionDetailById(pensionProductId, testUserId);
    }


    @Test
    @DisplayName("예금 조회수 증가: 성공")
    void testIncrementDepositViewCount() {
        String depositProductId = "D2";
        doNothing().when(productDetailMapper).incrementDepositViewCount(depositProductId);
        productDetailMapper.incrementDepositViewCount(depositProductId);
        verify(productDetailMapper, times(1)).incrementDepositViewCount(depositProductId);
    }

    @Test
    @DisplayName("적금 조회수 증가: 성공")
    void testIncrementSavingsViewCount() {
        String savingsProductId = "S3";
        doNothing().when(productDetailMapper).incrementSavingsViewCount(savingsProductId);
        productDetailMapper.incrementSavingsViewCount(savingsProductId);
        verify(productDetailMapper, times(1)).incrementSavingsViewCount(savingsProductId);
    }

    @Test
    @DisplayName("펀드 조회수 증가: 성공")
    void testIncrementFundViewCount() {
        String fundProductId = "F4";
        doNothing().when(productDetailMapper).incrementFundViewCount(fundProductId);
        productDetailMapper.incrementFundViewCount(fundProductId);
        verify(productDetailMapper, times(1)).incrementFundViewCount(fundProductId);
    }

    @Test
    @DisplayName("외환 조회수 증가: 성공")
    void testIncrementForexViewCount() {
        String forexProductId = "X5";
        doNothing().when(productDetailMapper).incrementForexViewCount(forexProductId);
        productDetailMapper.incrementForexViewCount(forexProductId);
        verify(productDetailMapper, times(1)).incrementForexViewCount(forexProductId);
    }

    @Test
    @DisplayName("연금 조회수 증가: 성공")
    void testIncrementPensionViewCount() {
        String pensionProductId = "C6"; // 안정형 연금 상품 ID (예시)
        doNothing().when(productDetailMapper).incrementPensionViewCount(pensionProductId);
        productDetailMapper.incrementPensionViewCount(pensionProductId);
        verify(productDetailMapper, times(1)).incrementPensionViewCount(pensionProductId);
    }
}