package com.halggeol.backend.insight.controller;

import com.halggeol.backend.insight.dto.*;
import com.halggeol.backend.insight.service.InsightDetailService;
import com.halggeol.backend.insight.service.InsightService;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@DisplayName("InsightController 단위 테스트")
class InsightControllerTest {

    @Mock
    private InsightService insightService;

    @Mock
    private InsightDetailService insightDetailService;

    @InjectMocks
    private InsightController insightController;

    private CustomUser mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // CustomUser 모킹
        User user = new User();
        user.setId(1);
        user.setEmail("kim01@example.com");  // username 역할
        user.setPassword("12341234");       // password 역할

        mockUser = new CustomUser(user);
    }

    @Test
    void getInsightList_withFundType() {
        List<InsightDTO> mockList = Arrays.asList(new InsightDTO(), new InsightDTO());
        Mockito.when(insightService.getFundInsightByUser(1L)).thenReturn(mockList);

        List<InsightDTO> result = insightController.getInsightList(mockUser, null, "fund");
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getInsightList_withRound() {
        List<InsightDTO> mockList = Arrays.asList(new InsightDTO());
        Mockito.when(insightService.getTop3MissedProducts(1, mockUser)).thenReturn(mockList);

        List<InsightDTO> result = insightController.getInsightList(mockUser, 1, null);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getInsightRoundsWithProducts() {
        List<InsightRoundWithProductsDTO> mockRounds = Collections.singletonList(new InsightRoundWithProductsDTO());
        Mockito.when(insightService.getAllRoundsWithProductsByUser(1L, null)).thenReturn(mockRounds);

        List<InsightRoundWithProductsDTO> result = insightController.getInsightRoundsWithProducts(mockUser, null).getBody();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getInsightDetail() {
        InsightDetailResponseDTO mockResponse = new InsightDetailResponseDTO();
        Mockito.when(insightDetailService.getInsightDetail(eq(1), eq("X0001"), eq(mockUser)))
                .thenReturn(mockResponse);

        InsightDetailResponseDTO result = (InsightDetailResponseDTO) insightController
                .getInsightDetail(1, "X0001", mockUser)
                .getBody();

        assertThat(result).isNotNull();
    }

    @Test
    void updateRegretSurvey() {
        RegretSurveyRequestDTO dto = new RegretSurveyRequestDTO();
        dto.setProductId("X0001");
        dto.setIsRegretted(true);
        dto.setRegrettedReason(2);

        // void 메서드이므로 doNothing()
        Mockito.doNothing().when(insightDetailService).updateRegretSurvey(mockUser, dto);

        assertThat(insightController.updateRegretSurvey(mockUser, dto).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getExchangeRates_withoutDate() {
        ExchangeRateDTO dto = new ExchangeRateDTO();
        dto.setCurUnit("USD");
        dto.setCurNm("미국 달러");
        dto.setDealBasR(BigDecimal.valueOf(1350.0));
        dto.setBaseDate("20250818");

        List<ExchangeRateDTO> mockRates = Arrays.asList(dto);
        Mockito.when(insightService.getExchangeRates(any())).thenReturn(mockRates);

        Map<String, Object> response = insightController.getExchangeRates(null).getBody();

        assertThat(response).isNotNull();
        assertThat(response.get("success")).isEqualTo(true);
        assertThat(((List<?>) response.get("data")).size()).isEqualTo(1);
    }

    @Test
    void fetchExchangeManually() {
        Mockito.doNothing().when(insightService).fetchAndSaveExchangeRates();

        assertThat(insightController.fetchExchangeManually().getBody())
                .isEqualTo("환율 데이터 수동 저장 완료");
    }
}
