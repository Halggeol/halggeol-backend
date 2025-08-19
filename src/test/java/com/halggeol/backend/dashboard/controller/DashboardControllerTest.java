package com.halggeol.backend.dashboard.controller;

import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.service.DashboardService;
import com.halggeol.backend.domain.CustomUser;
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
        mockUser = mock(CustomUser.class);

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
        // given
        when(dashboardService.getDashboardData(any(CustomUser.class))).thenReturn(mockResponseDTO);

        // when
        ResponseEntity<DashboardResponseDTO> responseEntity = dashboardController.getDashboard(mockUser);

        // then:
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getUserName()).isEqualTo("testuser");
        assertThat(responseEntity.getBody().getAvgRegretScore()).isEqualTo(10.5);
        assertThat(responseEntity.getBody().getAssets()).hasSize(1);
    }
}
