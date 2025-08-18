package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.domain.ProductType;
import com.halggeol.backend.insight.dto.*;
import com.halggeol.backend.insight.mapper.InsightDetailMapper;
import com.halggeol.backend.insight.service.strategy.InsightDetailFactory;
import com.halggeol.backend.insight.service.strategy.InsightDetailStrategy;
import com.halggeol.backend.insight.util.calculator.strategy.ProfitCalculatorFactory;
import com.halggeol.backend.logs.service.LogService;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsightDetailServiceImplTest {

    @Mock
    private InsightDetailMapper mapper;

    @Mock
    private RecommendService recommendService;

    @Mock
    private InsightDetailFactory factory;

    @Mock
    private ProfitCalculatorFactory calculatorFactory;

    @Mock
    private LogService logService;

    @InjectMocks
    private InsightDetailServiceImpl insightDetailService;

    private CustomUser testUser;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .name("테스트")
                .password("password")
                .build();
        testUser = new CustomUser(user);
    }

    @Test
    @DisplayName("getInsightDetail - FOREX 상품 테스트")
    void testGetInsightDetailForex() {
        // 준비
        String productId = "X0001"; // Forex 상품
        int userId = 1;

        InsightDetailResponseDTO baseInfo = InsightDetailResponseDTO.builder()
                .id(String.valueOf(1))
                .productName("Forex Test")
                .currency("USD,EUR")
                .recDate(LocalDate.now().minusDays(30))
                .anlzDate(LocalDate.now())
                .maxLimit(1_000_000L)
                .build();

        InsightDetailStrategy strategy = mock(InsightDetailStrategy.class);
        when(factory.getStrategy(ProductType.FOREX)).thenReturn(strategy); // <-- 중요!
        when(strategy.fetchProductInfo(anyInt(), eq(productId), anyInt())).thenReturn(baseInfo);

        // mapper Stub
        when(mapper.getAsset(userId, baseInfo.getRecDate())).thenReturn(1_000_000L);
        when(mapper.getExchangeRate("USD", baseInfo.getRecDate())).thenReturn(BigDecimal.valueOf(1300));
        when(mapper.getExchangeRate("USD", baseInfo.getAnlzDate())).thenReturn(BigDecimal.valueOf(1350));
        when(mapper.getExchangeRate("EUR", baseInfo.getRecDate())).thenReturn(BigDecimal.valueOf(1500));
        when(mapper.getExchangeRate("EUR", baseInfo.getAnlzDate())).thenReturn(BigDecimal.valueOf(1600));

        // 추천 상품 Stub
        when(recommendService.getSimilarProducts(productId)).thenReturn(List.of());

        // 실행
        InsightDetailResponseDTO result = insightDetailService.getInsightDetail(1, productId, testUser);

        // 검증
        assertNotNull(result);
        assertEquals("Forex Test", result.getProductName());
        assertNotNull(result.getForexInfo());
        assertEquals(2, result.getForexInfo().size()); // USD, EUR
        verify(mapper, times(1)).updateRegretInsight(anyMap());
    }

    @Test
    @DisplayName("updateRegretSurvey - 후회 있음")
    void testUpdateRegretSurveyRegret() {
        RegretSurveyRequestDTO survey = new RegretSurveyRequestDTO();
        survey.setProductId("P123");
        survey.setIsRegretted(true);
        survey.setRegrettedReason(2);

        insightDetailService.updateRegretSurvey(testUser, survey);

        ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
        verify(mapper).updateRegretSurvey(captor.capture());
        assertEquals("상품 설명이 부족하거나 복잡해서", captor.getValue().get("surveyResult"));
        verify(logService).buildLog("regret", "P123", 1);
    }

    @Test
    @DisplayName("updateRegretSurvey - 후회 없음")
    void testUpdateRegretSurveyNoRegret() {
        RegretSurveyRequestDTO survey = new RegretSurveyRequestDTO();
        survey.setProductId("P123");
        survey.setIsRegretted(false);

        insightDetailService.updateRegretSurvey(testUser, survey);

        ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
        verify(mapper).updateRegretSurvey(captor.capture());
        assertEquals("후회하지 않음", captor.getValue().get("surveyResult"));
        verify(logService).buildLog("no_regret", "P123", 1);
    }
}
