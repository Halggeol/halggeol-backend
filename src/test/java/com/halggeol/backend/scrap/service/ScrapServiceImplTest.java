package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.mapper.ScrapMapper;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("ScrapServiceImpl 테스트")
class ScrapServiceImplTest {

    @Mock
    private ScrapMapper scrapMapper;

    @InjectMocks
    @Spy
    private ScrapServiceImpl scrapService;

    private CustomUser testUser;
    private int testUserId;
    private ScrapRequestDTO testRequestDto;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .name("테스트 사용자")
                .password("password")
                .build();
        testUser = new CustomUser(user);
        testRequestDto = new ScrapRequestDTO();
    }


    @Test
    @DisplayName("스크랩 추가: 성공 (Deposit 상품)")
    void addScrapProduct_Deposit_Success() {
        // Given
        String productId = "D123";
        testRequestDto.setProductId(productId);
        Scrap expectedScrap = Scrap.builder()
            .userId(testUserId)
            .productId(productId)
            .build();

        doNothing().when(scrapMapper).insertUserScrap(any(Scrap.class)); // 어떤 Scrap 객체가 와도 허용
        doNothing().when(scrapMapper).incrementDepositScrapCount(anyString());

        // When
        scrapService.addScrapProduct(testUser, testRequestDto);

        // Then


        verify(scrapService, times(1)).incrementProductScrapCountAsync(productId);

        verify(scrapMapper, times(1)).insertUserScrap(eq(expectedScrap));

        verify(scrapMapper, times(1)).incrementDepositScrapCount(productId);
        verify(scrapMapper, never()).incrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).incrementFundScrapCount(anyString());
        verify(scrapMapper, never()).incrementForexScrapCount(anyString());
        verify(scrapMapper, never()).incrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("스크랩 추가: 성공 (Fund 상품)")
    void addScrapProduct_Fund_Success() {
        // Given
        String productId = "F456";
        testRequestDto.setProductId(productId);
        Scrap expectedScrap = Scrap.builder()
            .userId(testUserId)
            .productId(productId)
            .build();

        doNothing().when(scrapMapper).insertUserScrap(any(Scrap.class));
        doNothing().when(scrapMapper).incrementFundScrapCount(anyString()); // Fund 증가만 모의

        // When
        scrapService.addScrapProduct(testUser, testRequestDto);

        // Then
        verify(scrapService, times(1)).incrementProductScrapCountAsync(productId);
        verify(scrapMapper, times(1)).insertUserScrap(eq(expectedScrap));

        verify(scrapMapper, times(1)).incrementFundScrapCount(productId);
        verify(scrapMapper, never()).incrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).incrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).incrementForexScrapCount(anyString());
        verify(scrapMapper, never()).incrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("스크랩 삭제: 성공 (Savings 상품)")
    void removeScrapProduct_Savings_Success() {
        // Given
        String productId = "S789";
        testRequestDto.setProductId(productId);

        doNothing().when(scrapMapper).decrementSavingsScrapCount(anyString());

        doNothing().when(scrapMapper).deleteUserScrap(anyInt(), anyString());

        // When
        scrapService.removeScrapProduct(testUser, testRequestDto);

        // Then

        verify(scrapService, times(1)).decrementProductScrapCountAsync(productId);

        verify(scrapMapper, times(1)).deleteUserScrap(testUserId, productId);

        verify(scrapMapper, times(1)).decrementSavingsScrapCount(productId);
        verify(scrapMapper, never()).decrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).decrementFundScrapCount(anyString());
        verify(scrapMapper, never()).decrementForexScrapCount(anyString());
        verify(scrapMapper, never()).decrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("스크랩 삭제: 성공 (Pension 상품)")
    void removeScrapProduct_Pension_Success() {
        // Given
        String productId = "A012"; // Pension 상품 ID (A 또는 C)
        testRequestDto.setProductId(productId);

        doNothing().when(scrapMapper).decrementPensionScrapCount(anyString());
        doNothing().when(scrapMapper).deleteUserScrap(anyInt(), anyString());

        // When
        scrapService.removeScrapProduct(testUser, testRequestDto);

        // Then
        verify(scrapService, times(1)).decrementProductScrapCountAsync(productId);
        verify(scrapMapper, times(1)).deleteUserScrap(testUserId, productId);

        verify(scrapMapper, times(1)).decrementPensionScrapCount(productId);
        verify(scrapMapper, never()).decrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).decrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).decrementFundScrapCount(anyString());
        verify(scrapMapper, never()).decrementForexScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 증가 비동기: Deposit 상품, 올바른 매퍼 호출")
    void incrementProductScrapCountAsync_Deposit_CorrectMapperCall() {
        // Given
        String productId = "D111";
        doNothing().when(scrapMapper).incrementDepositScrapCount(anyString());

        // When
        scrapService.incrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).incrementDepositScrapCount(productId);
        verify(scrapMapper, never()).incrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).incrementFundScrapCount(anyString());
        verify(scrapMapper, never()).incrementForexScrapCount(anyString());
        verify(scrapMapper, never()).incrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 증가 비동기: Savings 상품, 올바른 매퍼 호출")
    void incrementProductScrapCountAsync_Savings_CorrectMapperCall() {
        // Given
        String productId = "S222";
        doNothing().when(scrapMapper).incrementSavingsScrapCount(anyString());

        // When
        scrapService.incrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).incrementSavingsScrapCount(productId);
        verify(scrapMapper, never()).incrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).incrementFundScrapCount(anyString());
        verify(scrapMapper, never()).incrementForexScrapCount(anyString());
        verify(scrapMapper, never()).incrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 증가 비동기: Fund 상품, 올바른 매퍼 호출")
    void incrementProductScrapCountAsync_Fund_CorrectMapperCall() {
        // Given
        String productId = "F333";
        doNothing().when(scrapMapper).incrementFundScrapCount(anyString());

        // When
        scrapService.incrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).incrementFundScrapCount(productId);
        verify(scrapMapper, never()).incrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).incrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).incrementForexScrapCount(anyString());
        verify(scrapMapper, never()).incrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 증가 비동기: Forex 상품, 올바른 매퍼 호출")
    void incrementProductScrapCountAsync_Forex_CorrectMapperCall() {
        // Given
        String productId = "X444";
        doNothing().when(scrapMapper).incrementForexScrapCount(anyString());

        // When
        scrapService.incrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).incrementForexScrapCount(productId);
        verify(scrapMapper, never()).incrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).incrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).incrementFundScrapCount(anyString());
        verify(scrapMapper, never()).incrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 증가 비동기: Pension 상품 (A), 올바른 매퍼 호출")
    void incrementProductScrapCountAsync_PensionA_CorrectMapperCall() {
        // Given
        String productId = "A555";
        doNothing().when(scrapMapper).incrementPensionScrapCount(anyString());

        // When
        scrapService.incrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).incrementPensionScrapCount(productId);
        verify(scrapMapper, never()).incrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).incrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).incrementFundScrapCount(anyString());
        verify(scrapMapper, never()).incrementForexScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 증가 비동기: Pension 상품 (C), 올바른 매퍼 호출")
    void incrementProductScrapCountAsync_PensionC_CorrectMapperCall() {
        // Given
        String productId = "C666";
        doNothing().when(scrapMapper).incrementPensionScrapCount(anyString());

        // When
        scrapService.incrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).incrementPensionScrapCount(productId);
        verify(scrapMapper, never()).incrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).incrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).incrementFundScrapCount(anyString());
        verify(scrapMapper, never()).incrementForexScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 감소 비동기: Deposit 상품, 올바른 매퍼 호출")
    void decrementProductScrapCountAsync_Deposit_CorrectMapperCall() {
        // Given
        String productId = "D777";
        doNothing().when(scrapMapper).decrementDepositScrapCount(anyString());

        // When
        scrapService.decrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).decrementDepositScrapCount(productId);
        verify(scrapMapper, never()).decrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).decrementFundScrapCount(anyString());
        verify(scrapMapper, never()).decrementForexScrapCount(anyString());
        verify(scrapMapper, never()).decrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 감소 비동기: Savings 상품, 올바른 매퍼 호출")
    void decrementProductScrapCountAsync_Savings_CorrectMapperCall() {
        // Given
        String productId = "S888";
        doNothing().when(scrapMapper).decrementSavingsScrapCount(anyString());

        // When
        scrapService.decrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).decrementSavingsScrapCount(productId);
        verify(scrapMapper, never()).decrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).decrementFundScrapCount(anyString());
        verify(scrapMapper, never()).decrementForexScrapCount(anyString());
        verify(scrapMapper, never()).decrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 감소 비동기: Fund 상품, 올바른 매퍼 호출")
    void decrementProductScrapCountAsync_Fund_CorrectMapperCall() {
        // Given
        String productId = "F999";
        doNothing().when(scrapMapper).decrementFundScrapCount(anyString());

        // When
        scrapService.decrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).decrementFundScrapCount(productId);
        verify(scrapMapper, never()).decrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).decrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).decrementForexScrapCount(anyString());
        verify(scrapMapper, never()).decrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 감소 비동기: Forex 상품, 올바른 매퍼 호출")
    void decrementProductScrapCountAsync_Forex_CorrectMapperCall() {
        // Given
        String productId = "X000";
        doNothing().when(scrapMapper).decrementForexScrapCount(anyString());

        // When
        scrapService.decrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).decrementForexScrapCount(productId);
        verify(scrapMapper, never()).decrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).decrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).decrementFundScrapCount(anyString());
        verify(scrapMapper, never()).decrementPensionScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 감소 비동기: Pension 상품 (A), 올바른 매퍼 호출")
    void decrementProductScrapCountAsync_PensionA_CorrectMapperCall() {
        // Given
        String productId = "A111";
        doNothing().when(scrapMapper).decrementPensionScrapCount(anyString());

        // When
        scrapService.decrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).decrementPensionScrapCount(productId);
        verify(scrapMapper, never()).decrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).decrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).decrementFundScrapCount(anyString());
        verify(scrapMapper, never()).decrementForexScrapCount(anyString());
    }

    @Test
    @DisplayName("관심수 감소 비동기: Pension 상품 (C), 올바른 매퍼 호출")
    void decrementProductScrapCountAsync_PensionC_CorrectMapperCall() {
        // Given
        String productId = "C222";
        doNothing().when(scrapMapper).decrementPensionScrapCount(anyString());

        // When
        scrapService.decrementProductScrapCountAsync(productId);

        // Then
        verify(scrapMapper, times(1)).decrementPensionScrapCount(productId);
        verify(scrapMapper, never()).decrementDepositScrapCount(anyString());
        verify(scrapMapper, never()).decrementSavingsScrapCount(anyString());
        verify(scrapMapper, never()).decrementFundScrapCount(anyString());
        verify(scrapMapper, never()).decrementForexScrapCount(anyString());
    }
}