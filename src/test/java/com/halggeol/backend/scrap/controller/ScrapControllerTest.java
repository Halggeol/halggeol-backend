package com.halggeol.backend.scrap.controller;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.service.ScrapService;
import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
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
        testRequestDto.setProductId("D123");
    }

    // --- 스크랩 추가 테스트 ---

    @Test
    @DisplayName("스크랩 추가: 성공 응답 확인")
    void addScrapProduct_Success() {
        // Given
        doNothing().when(scrapService).addScrapProduct(eq(testUser), any(ScrapRequestDTO.class));

        // When
        // 컨트롤러 메서드를 직접 호출 (HTTP 요청 시뮬레이션 아님)
        ResponseEntity<?> response = scrapController.addScrapProduct(testUser, testRequestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // scrapService.addScrapProduct가 올바른 인자로 1번 호출되었는지 검증
        verify(scrapService, times(1)).addScrapProduct(eq(testUser), eq(testRequestDto));
    }

    @Test
    @DisplayName("스크랩 추가: 실패 응답 (IllegalArgumentException)")
    void addScrapProduct_Failure_IllegalArgumentException() {
        // Given
        String errorMessage = "유효하지 않은 상품 ID입니다.";
        doThrow(new IllegalArgumentException(errorMessage))
            .when(scrapService).addScrapProduct(eq(testUser), any(ScrapRequestDTO.class));

        // When
        ResponseEntity<?> response = scrapController.addScrapProduct(testUser, testRequestDto);

        // Then
        // HTTP 상태 코드와 응답 본문(에러 메시지) 검증
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(errorMessage);

        verify(scrapService, times(1)).addScrapProduct(eq(testUser), eq(testRequestDto));
    }

    // --- 스크랩 삭제 테스트 ---

    @Test
    @DisplayName("스크랩 삭제: 성공 응답 확인")
    void removeScrapProduct_Success() {
        // Given
        doNothing().when(scrapService).removeScrapProduct(eq(testUser), any(ScrapRequestDTO.class));

        // When
        // 컨트롤러 메서드를 직접 호출
        ResponseEntity<?> response = scrapController.removeScrapProduct(testUser, testRequestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(scrapService, times(1)).removeScrapProduct(eq(testUser), eq(testRequestDto));
    }

    @Test
    @DisplayName("스크랩 삭제: 실패 응답 (IllegalArgumentException)")
    void removeScrapProduct_Failure_IllegalArgumentException() {
        // Given
        String errorMessage = "삭제할 스크랩을 찾을 수 없습니다.";
        doThrow(new IllegalArgumentException(errorMessage))
            .when(scrapService).removeScrapProduct(eq(testUser), any(ScrapRequestDTO.class));

        // When
        ResponseEntity<?> response = scrapController.removeScrapProduct(testUser, testRequestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(errorMessage);

        verify(scrapService, times(1)).removeScrapProduct(eq(testUser), eq(testRequestDto));
    }
}
