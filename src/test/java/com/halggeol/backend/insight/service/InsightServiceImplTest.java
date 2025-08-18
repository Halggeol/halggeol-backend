package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.ExchangeRateDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import com.halggeol.backend.insight.dto.InsightRoundDTO;
import com.halggeol.backend.insight.dto.InsightRoundWithProductsDTO;
import com.halggeol.backend.insight.mapper.InsightMapper;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.security.domain.CustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("InsightServiceImpl 및 관련 DTO 테스트")
class InsightServiceImplTest {

    @Mock
    private InsightMapper insightMapper;

    @InjectMocks
    private InsightServiceImpl insightService;

    private CustomUser testUser;
    private int testUserId;

    @BeforeEach
    void setUp() {
        testUserId = 1; // int 타입
        User user = User.builder()
                .id(testUserId) // int 타입 맞춤
                .email("test@example.com")
                .name("테스트 사용자")
                .password("password")
                .build();
        testUser = new CustomUser(user);
    }

    @Test
    @DisplayName("InsightDTO getter/setter 테스트")
    void testInsightDTOGetterSetter() {
        InsightDTO dto = new InsightDTO();
        dto.setProductId("F12345");
        dto.setName("테스트 펀드");
        dto.setCompany("테스트 회사");
        dto.setType("fund");
        dto.setRound(1);

        assertEquals("F12345", dto.getProductId());
        assertEquals("테스트 펀드", dto.getName());
        assertEquals("테스트 회사", dto.getCompany());
        assertEquals("fund", dto.getType());
        assertEquals(1, dto.getRound());
    }

    @Test
    @DisplayName("InsightRoundDTO Builder 테스트")
    void testInsightRoundDTOBuilder() {
        InsightRoundDTO round = InsightRoundDTO.builder()
                .round(1)
                .productIds(List.of("F12345", "F67890"))
                .recDate(LocalDateTime.now())
                .build();

        assertEquals(1, round.getRound());
        assertEquals(2, round.getProductIds().size());
        assertNotNull(round.getRecDate());
    }

    @Test
    @DisplayName("InsightRoundWithProductsDTO AllArgsConstructor 테스트")
    void testInsightRoundWithProductsDTOAllArgsConstructor() {
        InsightDTO product = new InsightDTO();
        InsightRoundWithProductsDTO dto = new InsightRoundWithProductsDTO(1, List.of(product), LocalDateTime.now());

        assertNotNull(dto);
        assertEquals(1, dto.getRound());
        assertEquals(1, dto.getProducts().size());
        assertNotNull(dto.getRecDate());
    }

    @Test
    @DisplayName("ExchangeRateDTO getter/setter 테스트")
    void testExchangeRateDTOGetterSetter() {
        ExchangeRateDTO dto = new ExchangeRateDTO();
        dto.setCurUnit("USD");
        dto.setDealBasR(BigDecimal.valueOf(1300));

        assertEquals("USD", dto.getCurUnit());
        assertEquals(BigDecimal.valueOf(1300), dto.getDealBasR());
    }

    // ===== 서비스 메서드 단위 테스트 =====

    @Test
    @DisplayName("사용자별 Top3 Missed Products 조회")
    void getTop3MissedProducts() {
        List<InsightDTO> mockList = List.of(new InsightDTO(), new InsightDTO());
        when(insightMapper.getTop3MissedProducts(1, testUserId)).thenReturn(mockList);

        List<InsightDTO> result = insightService.getTop3MissedProducts(1, testUser);

        assertEquals(2, result.size());
        verify(insightMapper, times(1)).getTop3MissedProducts(1, testUserId);
    }


    @Test
    @DisplayName("사용자별 펀드 인사이트 조회")
    void getFundInsightByUser() {
        List<InsightDTO> mockList = List.of(new InsightDTO());
        when(insightMapper.getFundInsightByUser((long) testUserId)).thenReturn(mockList);

        List<InsightDTO> result = insightService.getFundInsightByUser((long) testUserId);

        assertEquals(1, result.size());
        verify(insightMapper, times(1)).getFundInsightByUser((long) testUserId);
    }

    @Test
    @DisplayName("사용자별 공격형 연금 인사이트 조회")
    void getAggressivePensionInsightByUser() {
        List<InsightDTO> mockList = List.of(new InsightDTO());
        when(insightMapper.getAggressivePensionInsightByUser((long) testUserId)).thenReturn(mockList);

        List<InsightDTO> result = insightService.getAggressivePensionInsightByUser((long) testUserId);

        assertEquals(1, result.size());
        verify(insightMapper, times(1)).getAggressivePensionInsightByUser((long) testUserId);
    }

    @Test
    @DisplayName("사용자별 모든 InsightRound 조회")
    void getAllInsightRoundsByUserTest() {
        List<InsightRoundDTO> mockList = List.of(new InsightRoundDTO(1, List.of("F12345"), LocalDateTime.now()));
        when(insightMapper.getAllInsightRoundsByUser((long) testUserId)).thenReturn(mockList);

        List<InsightRoundDTO> result = insightService.getAllInsightRoundsByUser((long) testUserId);

        assertEquals(1, result.size());
        verify(insightMapper, times(1)).getAllInsightRoundsByUser((long) testUserId);
    }

    @Test
    @DisplayName("사용자별 모든 Round와 Products 조회")
    void getAllRoundsWithProductsByUser() {
        InsightRoundDTO roundDTO = new InsightRoundDTO(1, List.of("F12345"), LocalDateTime.now());
        when(insightMapper.getAllInsightRoundsByUser((long) testUserId)).thenReturn(List.of(roundDTO));

        InsightDTO product = new InsightDTO();
        when(insightMapper.getAllProductsByRoundAndUser(1, (long) testUserId)).thenReturn(List.of(product));

        List<InsightRoundWithProductsDTO> result = insightService.getAllRoundsWithProductsByUser((long) testUserId);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getProducts().size());
        verify(insightMapper, times(1)).getAllInsightRoundsByUser((long) testUserId);
        verify(insightMapper, times(1)).getAllProductsByRoundAndUser(1, (long) testUserId);
    }
}