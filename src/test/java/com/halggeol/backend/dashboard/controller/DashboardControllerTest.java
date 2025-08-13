package com.halggeol.backend.dashboard.controller;

import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.service.DashboardService;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Mockito를 사용하여 테스트 의존성을 관리합니다.
@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    // Mockito를 사용하여 DashboardService의 가짜(Mock) 객체를 생성합니다.
    @Mock
    private DashboardService dashboardService;

    // @Mock으로 생성된 가짜 객체를 DashboardController에 주입합니다.
    // 이것이 테스트 대상이 되는 실제 컨트롤러 객체입니다.
    @InjectMocks
    private DashboardController dashboardController;

    private CustomUser mockUser;
    private DashboardResponseDTO mockResponseDTO;

    @BeforeEach
    void setUp() {
        // @AuthenticationPrincipal로 전달될 CustomUser 객체를 Mockito로 생성합니다.
        // 이 방법은 CustomUser의 생성자를 알 필요가 없습니다.
        mockUser = mock(CustomUser.class);

        // DashboardResponseDTO의 실제 구조를 반영하여 Mockito 객체를 생성하고 필드를 설정합니다.
        // BDD(Builder) 패턴을 사용하여 가짜 데이터를 만듭니다.
        mockResponseDTO = DashboardResponseDTO.builder()
            .userName("testuser")
            .avgRegretScore(10.5)
            .assets(Collections.singletonList(new DashboardAssetResponseDTO()))
            .portfolio(Collections.emptyList())
            .regretRanking(Collections.singletonList(UnifiedProductRegretRankingResponseDTO.builder().build()))
            .recommendItems(Collections.singletonList(RecommendResponseDTO.builder().build()))
            .feedbackRatio(0.8)
            .build();
    }

    @Test
    @DisplayName("대시보드 데이터를 요청하면 HTTP 200과 올바른 DTO를 반환한다.")
    void getDashboard_Success_Returns200AndDTO() {
        // given: dashboardService.getDashboardData(any())가 호출되면 mockResponseDTO를 반환하도록 설정
        when(dashboardService.getDashboardData(any(CustomUser.class))).thenReturn(mockResponseDTO);

        // when: 컨트롤러의 메서드를 직접 호출하고 반환된 ResponseEntity를 받습니다.
        ResponseEntity<DashboardResponseDTO> responseEntity = dashboardController.getDashboard(mockUser);

        // then:
        // 1. 응답의 HTTP 상태 코드가 HttpStatus.OK (200)인지 확인합니다.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // 2. 응답 본문의 DTO가 null이 아닌지 확인합니다.
        assertThat(responseEntity.getBody()).isNotNull();
        // 3. 반환된 DTO의 userName 필드가 예상 값과 일치하는지 검증합니다.
        assertThat(responseEntity.getBody().getUserName()).isEqualTo("testuser");
        // 4. 반환된 DTO의 avgRegretScore 필드가 예상 값과 일치하는지 검증합니다.
        assertThat(responseEntity.getBody().getAvgRegretScore()).isEqualTo(10.5);
        // 5. 반환된 DTO의 assets 리스트의 크기가 예상 값과 일치하는지 검증합니다.
        assertThat(responseEntity.getBody().getAssets()).hasSize(1);
    }
}
