package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.dashboard.mapper.DashboardMapper;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.service.UnifiedProductService;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User; // 실제 User 클래스를 임포트
import com.halggeol.backend.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    // Mockito를 사용하여 필요한 의존성들을 가짜 객체로 만듭니다.
    @Mock
    private UserMapper userMapper;

    @Mock
    private DashboardMapper dashboardMapper;

    @Mock
    private RecommendService recommendService;

    @Mock
    private UnifiedProductService unifiedProductService;

    // @Mock 객체들을 DashboardServiceImpl에 주입합니다.
    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private List<UnifiedProductRegretRankingResponseDTO> mockRegretRanking;

    @BeforeEach
    void setUp() {
        // unifiedProductService가 반환할 가짜 데이터를 정의합니다.
        mockRegretRanking = Collections.singletonList(UnifiedProductRegretRankingResponseDTO.builder().build());
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 대시보드에 접근하면 추천 상품 목록만 반환한다.")
    void getDashboardData_anonymousUser_returnsOnlyRegretRanking() {
        // given: 로그인하지 않은 사용자 (user == null)
        when(unifiedProductService.getRegretRankingProducts()).thenReturn(mockRegretRanking);

        // when: getDashboardData 메서드를 호출합니다.
        DashboardResponseDTO result = dashboardService.getDashboardData(null);

        // then:
        // 1. 반환된 객체의 추천 상품 목록이 예상 값과 일치하는지 확인합니다.
        assertThat(result.getRegretRanking()).isEqualTo(mockRegretRanking);
        // 2. 다른 매퍼나 서비스는 호출되지 않았는지 검증합니다.
        verify(recommendService, never()).getRecommendProducts(anyString());
        verify(dashboardMapper, never()).getAvgRegretScoreByUserId(anyString());
        verify(userMapper, never()).findNameById(anyInt());
    }

    @Test
    @DisplayName("로그인한 사용자가 대시보드에 접근하면 모든 데이터를 반환한다.")
    void getDashboardData_loggedInUser_returnsAllData() {
        // given: 로그인한 사용자 (user != null)
        CustomUser mockUser = mock(CustomUser.class);
        User coreUser = mock(User.class);

        // 이 테스트에 필요한 스터빙만 여기에 작성합니다.
        when(mockUser.getUser()).thenReturn(coreUser);
        when(coreUser.getId()).thenReturn(1);
        when(unifiedProductService.getRegretRankingProducts()).thenReturn(mockRegretRanking);
        when(recommendService.getRecommendProducts(anyString())).thenReturn(Collections.emptyList());
        when(dashboardMapper.getAvgRegretScoreByUserId(anyString())).thenReturn(10.5);
        when(dashboardMapper.getAssetsOneYearByUserId(anyString())).thenReturn(Collections.emptyList());
        when(dashboardMapper.getPortfolioByUserId(anyString())).thenReturn(Collections.emptyList());
        when(userMapper.findNameById(anyInt())).thenReturn("testuser");
        when(dashboardMapper.getFeedbackRatioByUserId(anyString())).thenReturn(0.8);

        // when: getDashboardData 메서드를 호출합니다.
        DashboardResponseDTO result = dashboardService.getDashboardData(mockUser);

        // then:
        // 1. 반환된 객체의 각 필드 값이 예상 값과 일치하는지 확인합니다.
        assertThat(result.getUserName()).isEqualTo("testuser");
        assertThat(result.getAvgRegretScore()).isEqualTo(10.5);
        assertThat(result.getRegretRanking()).isEqualTo(mockRegretRanking);

        // 2. 모든 의존성 메서드가 한 번씩 호출되었는지 검증합니다.
        verify(recommendService).getRecommendProducts(anyString());
        verify(dashboardMapper).getAvgRegretScoreByUserId(anyString());
        verify(dashboardMapper).getAssetsOneYearByUserId(anyString());
        verify(dashboardMapper).getPortfolioByUserId(anyString());
        verify(userMapper).findNameById(anyInt());
        verify(dashboardMapper).getFeedbackRatioByUserId(anyString());
    }
}
