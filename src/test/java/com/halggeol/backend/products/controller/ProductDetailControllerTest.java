package com.halggeol.backend.products.controller;

import com.halggeol.backend.products.dto.ProductStatusRequestDTO;
import com.halggeol.backend.products.service.ProductDetailService;
import com.halggeol.backend.security.domain.CustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

// Mockito를 사용하여 테스트 의존성을 관리합니다.
@ExtendWith(MockitoExtension.class)
class ProductDetailControllerTest {

    @Mock
    private ProductDetailService productDetailService;

    @InjectMocks
    private ProductDetailController productDetailController;

    private CustomUser mockUser;
    private final String TEST_PRODUCT_ID = "product123";

    @BeforeEach
    void setUp() {
        // @AuthenticationPrincipal을 위한 Mock User 객체 생성
        mockUser = mock(CustomUser.class);
    }

    @Nested
    @DisplayName("getProductDetailById 메서드")
    class GetProductDetailByIdTest {

        @Test
        @DisplayName("유효한 productId로 상품 상세 조회 시 HTTP 200과 응답 객체를 반환한다.")
        void getProductDetailById_ValidProduct_Returns200AndResponse() {
            // given: 서비스가 어떤 객체든 반환하도록 설정
            Object mockResponse = new Object(); // 실제 DTO를 Mocking
            when(productDetailService.getProductDetailById(eq(TEST_PRODUCT_ID), any(CustomUser.class))).thenReturn(mockResponse);

            // when: 컨트롤러 메서드를 호출
            ResponseEntity<?> responseEntity = productDetailController.getProductDetailById(TEST_PRODUCT_ID, mockUser);

            // then:
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isSameAs(mockResponse);
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID로 조회 시 HTTP 404를 반환한다.")
        void getProductDetailById_NotFoundProduct_Returns404() {
            // given: 서비스가 null을 반환하도록 설정
            when(productDetailService.getProductDetailById(anyString(), any(CustomUser.class))).thenReturn(null);

            // when: 컨트롤러 메서드를 호출
            ResponseEntity<?> responseEntity = productDetailController.getProductDetailById(TEST_PRODUCT_ID, mockUser);

            // then:
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("서비스 계층에서 IllegalArgumentException 발생 시 HTTP 400을 반환한다.")
        void getProductDetailById_ServiceThrowsException_Returns400() {
            // given: 서비스가 예외를 던지도록 설정
            String errorMessage = "Invalid product ID format.";
            when(productDetailService.getProductDetailById(anyString(), any(CustomUser.class))).thenThrow(new IllegalArgumentException(errorMessage));

            // when: 컨트롤러 메서드를 호출
            ResponseEntity<?> responseEntity = productDetailController.getProductDetailById(TEST_PRODUCT_ID, mockUser);

            // then:
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(responseEntity.getBody()).isEqualTo(errorMessage);
        }
    }

    @Nested
    @DisplayName("checkRecommendProductStatus 메서드")
    class CheckRecommendProductStatusTest {

        @Test
        @DisplayName("추천 상품 상태 확인 시 HTTP 200과 상태 문자열을 반환한다.")
        void checkRecommendProductStatus_Success_Returns200AndStatus() {
            // given: 서비스가 상태 문자열을 반환하도록 설정
            String mockStatus = "RECOMMENDED";
            when(productDetailService.checkRecommendProductStatus(any(CustomUser.class), eq(TEST_PRODUCT_ID))).thenReturn(mockStatus);

            // when: 컨트롤러 메서드를 호출
            ResponseEntity<?> responseEntity = productDetailController.checkRecommendProductStatus(mockUser, TEST_PRODUCT_ID);

            // then:
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isEqualTo(mockStatus);
        }

        @Test
        @DisplayName("서비스 계층에서 IllegalArgumentException 발생 시 HTTP 400을 반환한다.")
        void checkRecommendProductStatus_ServiceThrowsException_Returns400() {
            // given: 서비스가 예외를 던지도록 설정
            when(productDetailService.checkRecommendProductStatus(any(CustomUser.class), anyString())).thenThrow(new IllegalArgumentException());

            // when: 컨트롤러 메서드를 호출
            ResponseEntity<?> responseEntity = productDetailController.checkRecommendProductStatus(mockUser, TEST_PRODUCT_ID);

            // then:
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    @DisplayName("updateProductStatus 메서드")
    class UpdateProductStatusTest {

        // Mockito를 사용하여 DTO 객체를 생성
        private final ProductStatusRequestDTO mockDto = mock(ProductStatusRequestDTO.class);

        @BeforeEach
        void innerSetUp() {
            // mockDto의 필드에 대한 동작을 미리 설정
            when(mockDto.getId()).thenReturn("1");
            when(mockDto.getStatus()).thenReturn("DONE");
        }

        @Test
        @DisplayName("상품 상태 업데이트 성공 시 HTTP 200을 반환한다.")
        void updateProductStatus_Success_Returns200() {
            // given: 서비스는 void 메서드이므로 별도 설정 불필요
            doNothing().when(productDetailService).updateProductStatus(any(CustomUser.class), anyString(), anyString());

            // when: 컨트롤러 메서드를 호출
            ResponseEntity<Void> responseEntity = productDetailController.updateProductStatus(mockUser, mockDto);

            // then:
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            // 서비스 메서드가 올바른 인자와 함께 호출되었는지 검증
            // Mockito.when()에서 설정한 값을 verify()에서 직접 사용하지 않고,
            // mockDto의 getter 메서드 호출을 Mockito.when()으로 스터빙한 상태를 활용합니다.
            verify(productDetailService, times(1)).updateProductStatus(eq(mockUser), eq("1"), eq("DONE"));
        }

        @Test
        @DisplayName("서비스 계층에서 IllegalArgumentException 발생 시 HTTP 400을 반환한다.")
        void updateProductStatus_ServiceThrowsException_Returns400() {
            // given: 서비스가 예외를 던지도록 설정
            doThrow(new IllegalArgumentException()).when(productDetailService).updateProductStatus(any(CustomUser.class), anyString(), anyString());

            // when: 컨트롤러 메서드를 호출
            ResponseEntity<Void> responseEntity = productDetailController.updateProductStatus(mockUser, mockDto);

            // then:
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
}
