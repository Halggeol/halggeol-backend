package com.halggeol.backend.insight.mapper;

import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("InsightDetailMapper Mockito 테스트")
class InsightDetailMapperTest {

    @Mock
    private InsightDetailMapper insightDetailMapper; // Mapper를 Mock으로

    private int testUserId;
    private int testRound;
    private String testProductId;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        testRound = 1;
        testProductId = "X0001";
    }

    @Test
    @DisplayName("예금 상품 상세 조회")
    void getDepositInfo() {
        InsightDetailResponseDTO mockResponse = InsightDetailResponseDTO.builder()
                .id(testProductId)
                .build();

        when(insightDetailMapper.getDepositInfo(testRound, testProductId, testUserId))
                .thenReturn(mockResponse);

        InsightDetailResponseDTO result = insightDetailMapper.getDepositInfo(testRound, testProductId, testUserId);

        assertThat(result.getId()).isEqualTo(testProductId);
        verify(insightDetailMapper, times(1))
                .getDepositInfo(testRound, testProductId, testUserId);
    }

    @Test
    @DisplayName("연금 상품 시뮬레이션 조회")
    void getProfitSimulation() {
        List<ProfitSimulationDTO> mockList = Arrays.asList(new ProfitSimulationDTO(), new ProfitSimulationDTO());

        when(insightDetailMapper.getProfitSimulation(eq(testRound), eq(testProductId), eq(testUserId),
                eq("deposit"), any(), any()))
                .thenReturn(mockList);

        List<ProfitSimulationDTO> result = insightDetailMapper.getProfitSimulation(
                testRound, testProductId, testUserId, "deposit", LocalDate.now(), LocalDate.now()
        );

        assertThat(result.size()).isEqualTo(2);
        verify(insightDetailMapper, times(1)).getProfitSimulation(eq(testRound), eq(testProductId),
                eq(testUserId), eq("deposit"), any(), any());
    }

    @Test
    @DisplayName("자산 조회")
    void getAsset() {
        when(insightDetailMapper.getAsset(eq(testUserId), any(LocalDate.class)))
                .thenReturn(10000L);

        Long result = insightDetailMapper.getAsset(testUserId, LocalDate.now());

        assertThat(result).isEqualTo(10000L);
        verify(insightDetailMapper, times(1)).getAsset(eq(testUserId), any(LocalDate.class));
    }

    @Test
    @DisplayName("환율 조회")
    void getExchangeRate() {
        when(insightDetailMapper.getExchangeRate(eq("USD"), any(LocalDate.class)))
                .thenReturn(BigDecimal.valueOf(1350));

        BigDecimal result = insightDetailMapper.getExchangeRate("USD", LocalDate.now());

        assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(1350));
        verify(insightDetailMapper, times(1))
                .getExchangeRate(eq("USD"), any(LocalDate.class));
    }

    @Test
    @DisplayName("Regret 설문 업데이트")
    void updateRegretSurvey() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", testUserId);
        params.put("productId", testProductId);
        params.put("regret", true);

        when(insightDetailMapper.updateRegretSurvey(params)).thenReturn(1);

        int updated = insightDetailMapper.updateRegretSurvey(params);

        assertThat(updated).isEqualTo(1);
        verify(insightDetailMapper, times(1)).updateRegretSurvey(params);
    }

    @Test
    @DisplayName("Regret 인사이트 업데이트")
    void updateRegretInsight() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", testUserId);
        params.put("productId", testProductId);
        params.put("feedback", "not good");

        doNothing().when(insightDetailMapper).updateRegretInsight(params);

        insightDetailMapper.updateRegretInsight(params);

        verify(insightDetailMapper, times(1)).updateRegretInsight(params);
    }
}
