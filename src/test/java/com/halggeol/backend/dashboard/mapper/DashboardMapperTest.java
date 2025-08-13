package com.halggeol.backend.dashboard.mapper;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardPortfolioResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardMapperTest {

    @Mock
    private SqlSessionTemplate sqlSessionTemplate;
    @InjectMocks
    private DashboardMapper dashboardMapper = new DashboardMapper() {
        @Override
        public Double getAvgRegretScoreByUserId(String userId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.dashboard.mapper.DashboardMapper.getAvgRegretScoreByUserId", userId);
        }

        @Override
        public List<DashboardAssetResponseDTO> getAssetsOneYearByUserId(String userId) {
            return sqlSessionTemplate.selectList("com.halggeol.backend.dashboard.mapper.DashboardMapper.getAssetsOneYearByUserId", userId);
        }

        @Override
        public List<DashboardPortfolioResponseDTO> getPortfolioByUserId(String userId) {
            return sqlSessionTemplate.selectList("com.halggeol.backend.dashboard.mapper.DashboardMapper.getPortfolioByUserId", userId);
        }

        @Override
        public Double getFeedbackRatioByUserId(String userId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.dashboard.mapper.DashboardMapper.getFeedbackRatioByUserId", userId);
        }
    };

    private final String TEST_USER_ID = "1";

    @Test
    @DisplayName("사용자 ID로 평균 후회 점수 조회 시 Double 값을 반환한다.")
    void getAvgRegretScoreByUserId_returnsDouble() {
        when(sqlSessionTemplate.selectOne(anyString(), anyString())).thenReturn(10.5);


        Double avgRegretScore = dashboardMapper.getAvgRegretScoreByUserId(TEST_USER_ID);

        // then:
        assertThat(avgRegretScore).isNotNull();
        assertThat(avgRegretScore).isEqualTo(10.5);
    }

    @Test
    @DisplayName("사용자 ID로 자산 목록 조회 시 DashboardAssetResponseDTO 리스트를 반환한다.")
    void getAssetsOneYearByUserId_returnsAssetList() {
        // given
        List<DashboardAssetResponseDTO> mockAssets = Collections.singletonList(new DashboardAssetResponseDTO());
        when(sqlSessionTemplate.selectList(anyString(), anyString())).thenReturn((List<Object>) (List<?>) mockAssets);

        // when
        List<DashboardAssetResponseDTO> assets = dashboardMapper.getAssetsOneYearByUserId(TEST_USER_ID);

        // then
        assertThat(assets).isNotNull();
        assertThat(assets).hasSize(1);
    }

    @Test
    @DisplayName("사용자 ID로 포트폴리오 목록 조회 시 DashboardPortfolioResponseDTO 리스트를 반환한다.")
    void getPortfolioByUserId_returnsPortfolioList() {
        // given
        List<DashboardPortfolioResponseDTO> mockPortfolio = Collections.emptyList();
        when(sqlSessionTemplate.selectList(anyString(), anyString())).thenReturn((List<Object>) (List<?>) mockPortfolio);

        // when
        List<DashboardPortfolioResponseDTO> portfolio = dashboardMapper.getPortfolioByUserId(TEST_USER_ID);

        // then
        assertThat(portfolio).isNotNull();
        assertThat(portfolio).hasSize(0);
    }

    @Test
    @DisplayName("사용자 ID로 피드백 비율 조회 시 Double 값을 반환한다.")
    void getFeedbackRatioByUserId_returnsDouble() {
        // given
        when(sqlSessionTemplate.selectOne(anyString(), anyString())).thenReturn(0.8);

        // when
        Double feedbackRatio = dashboardMapper.getFeedbackRatioByUserId(TEST_USER_ID);

        // then
        assertThat(feedbackRatio).isNotNull();
        assertThat(feedbackRatio).isEqualTo(0.8);
    }
}
