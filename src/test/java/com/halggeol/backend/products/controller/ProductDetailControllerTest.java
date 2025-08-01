//package com.halggeol.backend.products.controller;
//
//import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
//import com.halggeol.backend.products.service.ProductDetailService;
//import com.halggeol.backend.security.domain.CustomUser;
//import com.halggeol.backend.security.domain.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("ProductDetailController 테스트")
//class ProductDetailControllerTest {
//
//    @Mock
//    private ProductDetailService productDetailService;
//
//    @InjectMocks
//    private ProductDetailController productDetailController;
//
//    private CustomUser testUser;
//    private String testUserId;
//
//    @BeforeEach
//    void setUp() {
//        testUserId = "1";
//        User user = User.builder()
//                .id(1)
//                .email("test@example.com")
//                .name("테스트 사용자")
//                .password("password")
//                .build();
//        testUser = new CustomUser(user);
//    }
//
//    // --- 테스트 케이스 시작 ---
//
//    @Test
//    @DisplayName("상품 상세 조회 성공 - Deposit 상품")
//    void getProductDetailById_DepositProduct_Success() {
//        // Given
//        String productId = "D123";
//        DepositDetailResponseDTO mockResponse = new DepositDetailResponseDTO(); // 실제 DTO 객체로 대체
//        mockResponse.setId(productId);
//        mockResponse.setName("Test Deposit Product");
//
//        when(productDetailService.getProductDetailById(productId, testUser)).thenReturn(mockResponse);
//
//        // When
//        ResponseEntity<?> responseEntity = productDetailController.getProductDetailById(productId, testUser);
//
//        // Then
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        // 반환된 응답 본문이 mockResponse와 같은지 확인
//        assertThat(responseEntity.getBody()).isEqualTo(mockResponse);
//
//        verify(productDetailService, times(1)).getProductDetailById(productId, testUser);
//    }
//
//    @Test
//    @DisplayName("상품 상세 조회 실패 - 상품을 찾을 수 없음 (NOT_FOUND)")
//    void getProductDetailById_NotFound() {
//        // Given
//        String productId = "D999"; // 존재하지 않는 상품 ID 가정
//
//        when(productDetailService.getProductDetailById(productId, testUser)).thenReturn(null);
//
//        // When
//        ResponseEntity<?> responseEntity = productDetailController.getProductDetailById(productId, testUser);
//
//        // Then
//        // HTTP 상태 코드가 NOT_FOUND(404)인지 확인
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        // 응답 본문이 null인지 확인
//        assertThat(responseEntity.getBody()).isNull();
//
//        verify(productDetailService, times(1)).getProductDetailById(productId, testUser);
//    }
//
//    @Test
//    @DisplayName("상품 상세 조회 실패 - 유효하지 않은 상품 ID (BAD_REQUEST)")
//    void getProductDetailById_InvalidProductId_BadRequest() {
//        // Given
//        String invalidProductId = "Z123"; // 유효하지 않은 접두사 Z
//        String errorMessage = "Invalid product ID prefix: Z. Expected one of 'D', 'S', 'F', 'X', 'A', or 'C'.";
//
//        doThrow(new IllegalArgumentException(errorMessage))
//            .when(productDetailService)
//            .getProductDetailById(invalidProductId, testUser);
//
//        // When
//        ResponseEntity<?> responseEntity = productDetailController.getProductDetailById(invalidProductId, testUser);
//
//        // Then
//        // HTTP 상태 코드가 BAD_REQUEST(400)인지 확인
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        // 응답 본문이 예외 메시지와 같은지 확인
//        assertThat(responseEntity.getBody()).isEqualTo(errorMessage);
//
//        verify(productDetailService, times(1)).getProductDetailById(invalidProductId, testUser);
//    }
//}
