package com.halggeol.backend.scrap.controller;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.service.ScrapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScrapController Mockito 단위 테스트")
class ScrapControllerTest {

    @Mock
    private ScrapService scrapService;

    @InjectMocks
    private ScrapController scrapController;

    private int testUserId;
    private ScrapRequestDTO testRequestDto;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        testRequestDto = new ScrapRequestDTO();
        testRequestDto.setProductId("D123");
    }

    // --- 스크랩 추가 테스트 ---

    @Test
    @DisplayName("스크랩 추가: 성공 응답 확인")
    void addScrapProduct_Success() {
        // Given
        // scrapService.addScrapProduct 호출 시 0을 반환하도록 설정
        when(scrapService.addScrapProduct(eq(testUserId), any(ScrapRequestDTO.class)))
            .thenReturn(0);

        // When
        // 컨트롤러 메서드를 직접 호출 (HTTP 요청 시뮬레이션 아님)
        ResponseEntity<?> response = scrapController.addScrapProduct(testUserId, testRequestDto);

        // Then
        // HTTP 상태 코드와 응답 본문 검증
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(0);

        // scrapService.addScrapProduct가 올바른 인자로 1번 호출되었는지 검증
        verify(scrapService, times(1)).addScrapProduct(eq(testUserId), eq(testRequestDto));
    }

    @Test
    @DisplayName("스크랩 추가: 실패 응답 (IllegalArgumentException)")
    void addScrapProduct_Failure_IllegalArgumentException() {
        // Given
        String errorMessage = "유효하지 않은 상품 ID입니다.";
        // scrapService.addScrapProduct 호출 시 예외를 던지도록 설정
        when(scrapService.addScrapProduct(eq(testUserId), any(ScrapRequestDTO.class)))
            .thenThrow(new IllegalArgumentException(errorMessage));

        // When
        ResponseEntity<?> response = scrapController.addScrapProduct(testUserId, testRequestDto);

        // Then
        // HTTP 상태 코드와 응답 본문(에러 메시지) 검증
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(errorMessage);

        verify(scrapService, times(1)).addScrapProduct(eq(testUserId), eq(testRequestDto));
    }

    // --- 스크랩 삭제 테스트 ---

    @Test
    @DisplayName("스크랩 삭제: 성공 응답 확인")
    void removeScrapProduct_Success() {
        // Given
        // scrapService.removeScrapProduct 호출 시 0을 반환하도록 설정
        when(scrapService.removeScrapProduct(eq(testUserId), any(ScrapRequestDTO.class)))
            .thenReturn(0);

        // When
        // 컨트롤러 메서드를 직접 호출
        ResponseEntity<?> response = scrapController.removeScrapProduct(testUserId, testRequestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(0);

        verify(scrapService, times(1)).removeScrapProduct(eq(testUserId), eq(testRequestDto));
    }

    @Test
    @DisplayName("스크랩 삭제: 실패 응답 (IllegalArgumentException)")
    void removeScrapProduct_Failure_IllegalArgumentException() {
        // Given
        String errorMessage = "삭제할 스크랩을 찾을 수 없습니다.";
        // scrapService.removeScrapProduct 호출 시 예외를 던지도록 설정
        when(scrapService.removeScrapProduct(eq(testUserId), any(ScrapRequestDTO.class)))
            .thenThrow(new IllegalArgumentException(errorMessage));

        // When
        ResponseEntity<?> response = scrapController.removeScrapProduct(testUserId, testRequestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(errorMessage);

        verify(scrapService, times(1)).removeScrapProduct(eq(testUserId), eq(testRequestDto));
    }
}
