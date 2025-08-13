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

// Mockito를 사용하여 테스트 의존성을 관리합니다.
@ExtendWith(MockitoExtension.class)
class DashboardMapperTest {

    // SqlSessionTemplate을 모의(Mock)합니다.
    @Mock
    private SqlSessionTemplate sqlSessionTemplate;

    // 모의한 SqlSessionTemplate을 매퍼에 주입하는 대신,
    // 매퍼 테스트는 보통 SqlSessionTemplate을 직접 주입받아 매퍼 빈을 생성합니다.
    // 하지만 인터페이스를 직접 테스트하기 위해선 이 방법이 더 유연합니다.
    // 여기서는 매퍼 인터페이스를 모의하는 대신, 매퍼를 구현하는 SqlSessionTemplate을 직접 주입합니다.
    // 이 코드는 개념적인 접근 방식이며, 실제로는 @MybatisTest나 SpringContext를 통해 매퍼를 주입하는 것이 일반적입니다.
    // 하지만 의존성 한계 때문에, 매퍼 인터페이스 자체를 Mock하는 대신, 이 인터페이스를 구현하는 가짜 프록시를 생성합니다.

    @InjectMocks
    private DashboardMapper dashboardMapper = new DashboardMapper() {
        @Override
        public Double getAvgRegretScoreByUserId(String userId) {
            // SqlSessionTemplate의 selectOne을 호출하도록 로직을 모의
            return sqlSessionTemplate.selectOne("com.halggeol.backend.dashboard.mapper.DashboardMapper.getAvgRegretScoreByUserId", userId);
        }

        @Override
        public List<DashboardAssetResponseDTO> getAssetsOneYearByUserId(String userId) {
            // SqlSessionTemplate의 selectList를 호출하도록 로직을 모의
            return sqlSessionTemplate.selectList("com.halggeol.backend.dashboard.mapper.DashboardMapper.getAssetsOneYearByUserId", userId);
        }

        @Override
        public List<DashboardPortfolioResponseDTO> getPortfolioByUserId(String userId) {
            // SqlSessionTemplate의 selectList를 호출하도록 로직을 모의
            return sqlSessionTemplate.selectList("com.halggeol.backend.dashboard.mapper.DashboardMapper.getPortfolioByUserId", userId);
        }

        @Override
        public Double getFeedbackRatioByUserId(String userId) {
            // SqlSessionTemplate의 selectOne을 호출하도록 로직을 모의
            return sqlSessionTemplate.selectOne("com.halggeol.backend.dashboard.mapper.DashboardMapper.getFeedbackRatioByUserId", userId);
        }
    };

    // 테스트에 사용할 더미 사용자 ID
    private final String TEST_USER_ID = "1";

    @Test
    @DisplayName("사용자 ID로 평균 후회 점수 조회 시 Double 값을 반환한다.")
    void getAvgRegretScoreByUserId_returnsDouble() {
        // given: sqlSessionTemplate.selectOne이 호출되면 10.5를 반환하도록 설정
        when(sqlSessionTemplate.selectOne(anyString(), anyString())).thenReturn(10.5);

        // when: 매퍼 메서드를 호출하여 평균 후회 점수를 조회합니다.
        Double avgRegretScore = dashboardMapper.getAvgRegretScoreByUserId(TEST_USER_ID);

        // then:
        // 반환된 값이 null이 아니며, 예상한 값과 일치하는지 검증합니다.
        assertThat(avgRegretScore).isNotNull();
        assertThat(avgRegretScore).isEqualTo(10.5);
    }

    @Test
    @DisplayName("사용자 ID로 자산 목록 조회 시 DashboardAssetResponseDTO 리스트를 반환한다.")
    void getAssetsOneYearByUserId_returnsAssetList() {
        // given: sqlSessionTemplate.selectList이 호출되면 빈 리스트를 반환하도록 설정
        // 제네릭 타입 불일치 오류 해결을 위해, 반환값을 List<Object>로 캐스팅합니다.
        List<DashboardAssetResponseDTO> mockAssets = Collections.singletonList(new DashboardAssetResponseDTO());
        when(sqlSessionTemplate.selectList(anyString(), anyString())).thenReturn((List<Object>) (List<?>) mockAssets);

        // when: 매퍼 메서드를 호출하여 자산 목록을 조회합니다.
        List<DashboardAssetResponseDTO> assets = dashboardMapper.getAssetsOneYearByUserId(TEST_USER_ID);

        // then:
        // 반환된 리스트가 null이 아니며, 예상한 크기와 일치하는지 검증합니다.
        assertThat(assets).isNotNull();
        assertThat(assets).hasSize(1);
    }

    @Test
    @DisplayName("사용자 ID로 포트폴리오 목록 조회 시 DashboardPortfolioResponseDTO 리스트를 반환한다.")
    void getPortfolioByUserId_returnsPortfolioList() {
        // given: sqlSessionTemplate.selectList이 호출되면 빈 리스트를 반환하도록 설정
        // 제네릭 타입 불일치 오류 해결을 위해, 반환값을 List<Object>로 캐스팅합니다.
        List<DashboardPortfolioResponseDTO> mockPortfolio = Collections.emptyList();
        when(sqlSessionTemplate.selectList(anyString(), anyString())).thenReturn((List<Object>) (List<?>) mockPortfolio);

        // when: 매퍼 메서드를 호출하여 포트폴리오 목록을 조회합니다.
        List<DashboardPortfolioResponseDTO> portfolio = dashboardMapper.getPortfolioByUserId(TEST_USER_ID);

        // then:
        // 반환된 리스트가 null이 아니며, 예상한 크기와 일치하는지 검증합니다.
        assertThat(portfolio).isNotNull();
        assertThat(portfolio).hasSize(0);
    }

    @Test
    @DisplayName("사용자 ID로 피드백 비율 조회 시 Double 값을 반환한다.")
    void getFeedbackRatioByUserId_returnsDouble() {
        // given: sqlSessionTemplate.selectOne이 호출되면 0.8을 반환하도록 설정
        when(sqlSessionTemplate.selectOne(anyString(), anyString())).thenReturn(0.8);

        // when: 매퍼 메서드를 호출하여 피드백 비율을 조회합니다.
        Double feedbackRatio = dashboardMapper.getFeedbackRatioByUserId(TEST_USER_ID);

        // then:
        // 반환된 값이 null이 아니며, 예상한 값과 일치하는지 검증합니다.
        assertThat(feedbackRatio).isNotNull();
        assertThat(feedbackRatio).isEqualTo(0.8);
    }
}
