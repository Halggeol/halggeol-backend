package com.halggeol.backend.products.service;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.products.dto.*;
import com.halggeol.backend.products.mapper.ProductDetailMapper;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.service.UnifiedProductService;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User; // 실제 User 클래스를 임포트
import com.halggeol.backend.logs.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDetailServiceImplTest {

    @Mock
    private ProductDetailMapper productDetailMapper;
    @Mock
    private RecommendService recommendService;
    @Mock
    private LogService logService;
    @Mock
    private UnifiedProductService unifiedProductService;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;
    @Mock
    private Cache.ValueWrapper valueWrapper;

    @InjectMocks
    private ProductDetailServiceImpl productDetailService;

    private final String TEST_PRODUCT_ID_DEPOSIT = "D001";
    private final String TEST_PRODUCT_ID_SAVINGS = "S001";
    private final String TEST_USER_ID = "1";

    @Nested
    @DisplayName("getProductDetailById 메서드")
    class GetProductDetailByIdTest {
        @Test
        @DisplayName("로그인 사용자의 캐시 미스 시, 매퍼를 호출하고 점수와 사용자 데이터를 포함한 DTO를 반환한다.")
        void getProductDetailById_loggedInUser_cacheMiss_returnsDtoWithUserDataAndScore() {
            // given
            CustomUser mockUser = mock(CustomUser.class);
            User coreUser = mock(User.class);
            when(mockUser.getUser()).thenReturn(coreUser);
            when(coreUser.getId()).thenReturn(1);

            when(cacheManager.getCache(anyString())).thenReturn(cache);
            when(cache.get(anyString())).thenReturn(null); // Cache Miss
            DepositDetailResponseDTO baseDto = new DepositDetailResponseDTO();
            when(productDetailMapper.selectBaseDepositDetailById(TEST_PRODUCT_ID_DEPOSIT)).thenReturn(baseDto);
            UserSpecificDataResponseDTO userDto = new UserSpecificDataResponseDTO();
            userDto.setAdvantage("Advantage");
            userDto.setDisadvantage("Disadvantage");
            userDto.setScraped(true);
            when(productDetailMapper.selectUserSpecificDataForDeposit(TEST_USER_ID, TEST_PRODUCT_ID_DEPOSIT)).thenReturn(userDto);
            when(recommendService.getProductMatchScore(TEST_PRODUCT_ID_DEPOSIT, TEST_USER_ID)).thenReturn(0.85);

            // when
            DepositDetailResponseDTO result = (DepositDetailResponseDTO) productDetailService.getProductDetailById(TEST_PRODUCT_ID_DEPOSIT, mockUser);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getAdvantage()).isEqualTo("Advantage");
            assertThat(result.getDisadvantage()).isEqualTo("Disadvantage");

            assertThat(result.getIsScraped()).isTrue();
            assertThat(result.getScore()).isEqualTo(85);
            verify(cache, times(1)).put(eq(TEST_PRODUCT_ID_DEPOSIT), any(DepositDetailResponseDTO.class));
            verify(logService, times(1)).buildLog(eq("view"), eq(TEST_PRODUCT_ID_DEPOSIT), eq(1));
        }

        @Test
        @DisplayName("로그인 사용자의 캐시 히트 시, 매퍼를 호출하지 않고 점수와 사용자 데이터를 포함한 DTO를 반환한다.")
        void getProductDetailById_loggedInUser_cacheHit_returnsDtoWithUserDataAndScore() {
            // given
            CustomUser mockUser = mock(CustomUser.class);
            User coreUser = mock(User.class);
            when(mockUser.getUser()).thenReturn(coreUser);
            when(coreUser.getId()).thenReturn(1);

            when(cacheManager.getCache(anyString())).thenReturn(cache);
            when(cache.get(anyString())).thenReturn(valueWrapper); // Cache Hit
            DepositDetailResponseDTO baseDto = new DepositDetailResponseDTO();
            when(valueWrapper.get()).thenReturn(baseDto);
            UserSpecificDataResponseDTO userDto = new UserSpecificDataResponseDTO();
            when(productDetailMapper.selectUserSpecificDataForDeposit(TEST_USER_ID, TEST_PRODUCT_ID_DEPOSIT)).thenReturn(userDto);
            when(recommendService.getProductMatchScore(TEST_PRODUCT_ID_DEPOSIT, TEST_USER_ID)).thenReturn(0.90);

            // when
            DepositDetailResponseDTO result = (DepositDetailResponseDTO) productDetailService.getProductDetailById(TEST_PRODUCT_ID_DEPOSIT, mockUser);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getScore()).isEqualTo(90);
            verify(productDetailMapper, never()).selectBaseDepositDetailById(anyString()); // 매퍼 호출 안함
            verify(logService, times(1)).buildLog(eq("view"), eq(TEST_PRODUCT_ID_DEPOSIT), eq(1));
        }

        @Test
        @DisplayName("익명 사용자의 캐시 미스 시, 매퍼를 호출하고 기본 DTO를 반환한다.")
        void getProductDetailById_anonymousUser_cacheMiss_returnsBaseDto() {
            // given
            when(cacheManager.getCache(anyString())).thenReturn(cache);
            when(cache.get(anyString())).thenReturn(null); // Cache Miss
            SavingsDetailResponseDTO baseDto = new SavingsDetailResponseDTO();
            when(productDetailMapper.selectBaseSavingsDetailById(TEST_PRODUCT_ID_SAVINGS)).thenReturn(baseDto);

            // when
            SavingsDetailResponseDTO result = (SavingsDetailResponseDTO) productDetailService.getProductDetailById(TEST_PRODUCT_ID_SAVINGS, null);

            // then
            assertThat(result).isNotNull();
            verify(recommendService, never()).getProductMatchScore(anyString(), anyString());
            verify(productDetailMapper, never()).selectUserSpecificDataForSavings(anyString(), anyString());
            verify(cache, times(1)).put(eq(TEST_PRODUCT_ID_SAVINGS), any(SavingsDetailResponseDTO.class));
            verify(logService, never()).buildLog(anyString(), anyString(), anyInt());
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID로 조회 시 null을 반환한다.")
        void getProductDetailById_nonExistingProduct_returnsNull() {
            // given
            when(cacheManager.getCache(anyString())).thenReturn(cache);
            when(cache.get(anyString())).thenReturn(null);
            when(productDetailMapper.selectBaseSavingsDetailById(anyString())).thenReturn(null);

            // when
            Object result = productDetailService.getProductDetailById("S999", null);

            // then
            assertThat(result).isNull();
            verify(cache, never()).put(anyString(), any());
        }
    }

    @Nested
    @DisplayName("checkRecommendProductStatus 메서드")
    class CheckRecommendProductStatusTest {
        @Test
        @DisplayName("사용자 ID와 상품 ID로 상품 상태를 조회한다.")
        void checkRecommendProductStatus_returnsStatus() {
            // given
            CustomUser mockUser = mock(CustomUser.class);
            User coreUser = mock(User.class);
            when(mockUser.getUser()).thenReturn(coreUser);
            when(coreUser.getId()).thenReturn(1);

            when(productDetailMapper.selectProductStatus(anyInt(), anyString())).thenReturn("DONE");

            // when
            String result = productDetailService.checkRecommendProductStatus(mockUser, TEST_PRODUCT_ID_DEPOSIT);

            // then
            assertThat(result).isEqualTo("DONE");
            verify(productDetailMapper, times(1)).selectProductStatus(eq(1), eq(TEST_PRODUCT_ID_DEPOSIT));
        }
    }

    @Nested
    @DisplayName("updateProductStatus 메서드")
    class UpdateProductStatusTest {
        @Test
        @DisplayName("사용자 ID와 상품 ID, 상태로 상품 상태를 업데이트한다.")
        void updateProductStatus_updatesStatus() {
            // given
            CustomUser mockUser = mock(CustomUser.class);
            User coreUser = mock(User.class);
            when(mockUser.getUser()).thenReturn(coreUser);
            when(coreUser.getId()).thenReturn(1);

            String status = "DONE";
            doNothing().when(productDetailMapper).updateProductStatus(anyInt(), anyString(), anyString());

            // when
            productDetailService.updateProductStatus(mockUser, TEST_PRODUCT_ID_DEPOSIT, status);

            // then
            verify(productDetailMapper, times(1)).updateProductStatus(eq(1), eq(TEST_PRODUCT_ID_DEPOSIT), eq(status));
        }
    }
}
