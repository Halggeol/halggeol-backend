package com.halggeol.backend.insight.mapper;

import com.halggeol.backend.insight.dto.ExchangeRateDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import com.halggeol.backend.insight.dto.InsightRoundDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("InsightMapper Mockito 테스트")
class InsightMapperTest {

    @Mock
    private InsightMapper insightMapper;

    private final int testUserId = 1;

    // --- Top3 Missed Products ---
    @Test
    @DisplayName("Top3 Missed Products 조회")
    void getTop3MissedProducts() {
        int round = 1;
        List<InsightDTO> mockList = Arrays.asList(new InsightDTO(), new InsightDTO(), new InsightDTO());

        when(insightMapper.getTop3MissedProducts(round, testUserId)).thenReturn(mockList);

        List<InsightDTO> result = insightMapper.getTop3MissedProducts(round, testUserId);

        assertThat(result.size()).isEqualTo(3);
        verify(insightMapper, times(1)).getTop3MissedProducts(round, testUserId);
    }

    // --- 펀드 인사이트 조회 ---
    @Test
    @DisplayName("사용자 펀드 인사이트 조회")
    void getFundInsightByUser() {
        List<InsightDTO> mockList = Arrays.asList(new InsightDTO(), new InsightDTO());

        when(insightMapper.getFundInsightByUser((long)testUserId)).thenReturn(mockList);

        List<InsightDTO> result = insightMapper.getFundInsightByUser((long)testUserId);

        assertThat(result.size()).isEqualTo(2);
        verify(insightMapper, times(1)).getFundInsightByUser((long)testUserId);
    }

    // --- 공격형 연금 인사이트 조회 ---
    @Test
    @DisplayName("사용자 공격형 연금 인사이트 조회")
    void getAggressivePensionInsightByUser() {
        List<InsightDTO> mockList = Collections.singletonList(new InsightDTO());

        when(insightMapper.getAggressivePensionInsightByUser((long)testUserId)).thenReturn(mockList);

        List<InsightDTO> result = insightMapper.getAggressivePensionInsightByUser((long)testUserId);

        assertThat(result.size()).isEqualTo(1);
        verify(insightMapper, times(1)).getAggressivePensionInsightByUser((long)testUserId);
    }

    // --- 특정 날짜 환율 조회 ---
    @Test
    @DisplayName("특정 날짜 환율 조회")
    void getForexRateOnDate() {
        BigDecimal mockRate = BigDecimal.valueOf(1350.0);
        LocalDate recDate = LocalDate.of(2025, 8, 18);

        when(insightMapper.getForexRateOnDate("X0001", recDate, "USD")).thenReturn(mockRate);

        BigDecimal rate = insightMapper.getForexRateOnDate("X0001", recDate, "USD");

        assertThat(rate).isEqualTo(mockRate);
        verify(insightMapper, times(1)).getForexRateOnDate("X0001", recDate, "USD");
    }

    // --- 가장 최근 환율 조회 ---
    @Test
    @DisplayName("최근 환율 조회")
    void getLatestForexRateBeforeDate() {
        BigDecimal mockRate = BigDecimal.valueOf(1348.5);
        LocalDate recDate = LocalDate.of(2025, 8, 18);

        when(insightMapper.getLatestForexRateBeforeDate("X0001", "USD", recDate)).thenReturn(mockRate);

        BigDecimal rate = insightMapper.getLatestForexRateBeforeDate("X0001", "USD", recDate);

        assertThat(rate).isEqualTo(mockRate);
        verify(insightMapper, times(1)).getLatestForexRateBeforeDate("X0001", "USD", recDate);
    }

    // --- 환율 존재 여부 ---
    @Test
    @DisplayName("환율 존재 여부 확인")
    void existsExchangeRate() {
        when(insightMapper.existsExchangeRate("USD", "20250818")).thenReturn(true);

        boolean exists = insightMapper.existsExchangeRate("USD", "20250818");

        assertThat(exists).isTrue();
        verify(insightMapper, times(1)).existsExchangeRate("USD", "20250818");
    }

    // --- 환율 INSERT ---
    @Test
    @DisplayName("환율 INSERT")
    void insertExchangeRate() {
        ExchangeRateDTO dto = new ExchangeRateDTO();
        dto.setCurUnit("USD");
        dto.setBaseDate("20250818");
        dto.setDealBasR(BigDecimal.valueOf(1350.0));
        dto.setCurNm("미국 달러");

        doNothing().when(insightMapper).insertExchangeRate(dto);

        insightMapper.insertExchangeRate(dto);

        verify(insightMapper, times(1)).insertExchangeRate(dto);
    }

    // --- Insight Round 조회 ---
    @Test
    @DisplayName("사용자 인사이트 라운드 조회")
    void getAllInsightRoundsByUser() {
        List<InsightRoundDTO> mockRounds = Arrays.asList(new InsightRoundDTO(), new InsightRoundDTO());

        when(insightMapper.getAllInsightRoundsByUser((long)testUserId)).thenReturn(mockRounds);

        List<InsightRoundDTO> result = insightMapper.getAllInsightRoundsByUser((long)testUserId);

        assertThat(result.size()).isEqualTo(2);
        verify(insightMapper, times(1)).getAllInsightRoundsByUser((long)testUserId);
    }

    // --- Round별 상품 조회 ---
    @Test
    @DisplayName("라운드별 상품 조회")
    void getAllProductsByRoundAndUser() {
        int round = 1;
        List<InsightDTO> mockList = Arrays.asList(new InsightDTO(), new InsightDTO());

        when(insightMapper.getAllProductsByRoundAndUser(round, (long)testUserId)).thenReturn(mockList);

        List<InsightDTO> result = insightMapper.getAllProductsByRoundAndUser(round, (long)testUserId);

        assertThat(result.size()).isEqualTo(2);
        verify(insightMapper, times(1)).getAllProductsByRoundAndUser(round, (long)testUserId);
    }
}
