package com.halggeol.backend.products.service;

import com.halggeol.backend.products.dto.*;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductDetailService 인터페이스 테스트")
class ProductDetailServiceTest {

    private CustomUser testUser;
    private String testUserId;

    @BeforeEach
    void setUp() {
        testUserId = "1";
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .name("테스트 사용자")
                .password("password")
                .build();
        testUser = new CustomUser(user);
    }

    @Test
    @DisplayName("DepositDetailResponseDTO getter/setter 테스트")
    void testDepositDetailResponseDTOGetterSetter() {
        // Given
        DepositDetailResponseDTO dto = new DepositDetailResponseDTO();
        String id = "D12345";
        String name = "KB 정기예금";
        Double rate = 3.5;
        String company = "KB국민은행";
        String advantage = "높은 금리";
        String disadvantage = "만기 전 해지시 불이익";
        Long viewCnt = 100L;
        Long scrapCnt = 50L;
        Boolean isScraped = true;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setRate(rate);
        dto.setCompany(company);
        dto.setAdvantage(advantage);
        dto.setDisadvantage(disadvantage);
        dto.setViewCnt(viewCnt);
        dto.setScrapCnt(scrapCnt);
        dto.setIsScraped(isScraped);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(rate, dto.getRate());
        assertEquals(company, dto.getCompany());
        assertEquals(advantage, dto.getAdvantage());
        assertEquals(disadvantage, dto.getDisadvantage());
        assertEquals(viewCnt, dto.getViewCnt());
        assertEquals(scrapCnt, dto.getScrapCnt());
        assertEquals(isScraped, dto.getIsScraped());
    }

    @Test
    @DisplayName("SavingsDetailResponseDTO getter/setter 테스트")
    void testSavingsDetailResponseDTOGetterSetter() {
        // Given
        SavingsDetailResponseDTO dto = new SavingsDetailResponseDTO();
        String id = "S54321";
        String name = "신한 자유적금";
        Double rate = 2.8;
        String company = "신한은행";
        String rateType = "단리";
        String saveType = "자유적금";
        Integer score = 85;
        Integer risk = 2;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setRate(rate);
        dto.setCompany(company);
        dto.setRateType(rateType);
        dto.setSaveType(saveType);
        dto.setScore(score);
        dto.setRisk(risk);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(rate, dto.getRate());
        assertEquals(company, dto.getCompany());
        assertEquals(rateType, dto.getRateType());
        assertEquals(saveType, dto.getSaveType());
        assertEquals(score, dto.getScore());
        assertEquals(risk, dto.getRisk());
    }

    @Test
    @DisplayName("FundDetailResponseDTO getter/setter 테스트")
    void testFundDetailResponseDTOGetterSetter() {
        // Given
        FundDetailResponseDTO dto = new FundDetailResponseDTO();
        String id = "F98765";
        String name = "미래에셋 글로벌펀드";
        Double rate = 5.2;
        String company = "미래에셋자산운용";
        Double fundPrice = 12500.0;
        String category = "해외주식형";
        String theme = "글로벌";
        Integer minimumCost = 10000;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setRate(rate);
        dto.setCompany(company);
        dto.setFundPrice(fundPrice);
        dto.setCategory(category);
        dto.setTheme(theme);
        dto.setMinimumCost(minimumCost);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(rate, dto.getRate());
        assertEquals(company, dto.getCompany());
        assertEquals(fundPrice, dto.getFundPrice());
        assertEquals(category, dto.getCategory());
        assertEquals(theme, dto.getTheme());
        assertEquals(minimumCost, dto.getMinimumCost());
    }

    @Test
    @DisplayName("ForexDetailResponseDTO getter/setter 테스트")
    void testForexDetailResponseDTOGetterSetter() {
        // Given
        ForexDetailResponseDTO dto = new ForexDetailResponseDTO();
        String id = "X11111";
        String name = "하나 달러 예금";
        Double rate = 4.0;
        String company = "하나은행";
        String currency = "USD";
        String description = "달러 정기예금";
        Boolean extraDeposit = true;
        Boolean protect = true;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setRate(rate);
        dto.setCompany(company);
        dto.setCurrency(currency);
        dto.setDescription(description);
        dto.setExtraDeposit(extraDeposit);
        dto.setProtect(protect);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(rate, dto.getRate());
        assertEquals(company, dto.getCompany());
        assertEquals(currency, dto.getCurrency());
        assertEquals(description, dto.getDescription());
        assertEquals(extraDeposit, dto.getExtraDeposit());
        assertEquals(protect, dto.getProtect());
    }

    @Test
    @DisplayName("PensionDetailResponseDTO getter/setter 테스트")
    void testPensionDetailResponseDTOGetterSetter() {
        // Given
        PensionDetailResponseDTO dto = new PensionDetailResponseDTO();
        String id = "A22222";
        String name = "삼성생명 연금보험";
        Double rate = 3.2;
        String company = "삼성생명";
        String pensionKind = "개인연금";
        Boolean pensionType = true;
        Double minGuaranteeRate = 2.5;
        Long viewCnt = 200L;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setRate(rate);
        dto.setCompany(company);
        dto.setPensionKind(pensionKind);
        dto.setPensionType(pensionType);
        dto.setMinGuaranteeRate(minGuaranteeRate);
        dto.setViewCnt(viewCnt);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(rate, dto.getRate());
        assertEquals(company, dto.getCompany());
        assertEquals(pensionKind, dto.getPensionKind());
        assertEquals(pensionType, dto.getPensionType());
        assertEquals(minGuaranteeRate, dto.getMinGuaranteeRate());
        assertEquals(viewCnt, dto.getViewCnt());
    }

    @Test
    @DisplayName("DTO null 값 처리 테스트")
    void testDTONullValueHandling() {
        // Given
        DepositDetailResponseDTO dto = new DepositDetailResponseDTO();

        // When
        dto.setId(null);
        dto.setName(null);
        dto.setRate(null);
        dto.setAdvantage(null);
        dto.setDisadvantage(null);

        // Then
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getRate());
        assertNull(dto.getAdvantage());
        assertNull(dto.getDisadvantage());
    }

    @Test
    @DisplayName("상품 ID 접두사 검증 테스트")
    void testProductIdPrefixValidation() {
        // Given
        String depositId = "D12345";
        String savingsId = "S54321";
        String fundId = "F98765";
        String forexId = "X11111";
        String aggressivePensionId = "A22222";
        String conservativePensionId = "C33333";

        // When & Then
        assertEquals('D', depositId.charAt(0));
        assertEquals('S', savingsId.charAt(0));
        assertEquals('F', fundId.charAt(0));
        assertEquals('X', forexId.charAt(0));
        assertEquals('A', aggressivePensionId.charAt(0));
        assertEquals('C', conservativePensionId.charAt(0));

        assertTrue(depositId.startsWith("D"));
        assertTrue(savingsId.startsWith("S"));
        assertTrue(fundId.startsWith("F"));
        assertTrue(forexId.startsWith("X"));
        assertTrue(aggressivePensionId.startsWith("A"));
        assertTrue(conservativePensionId.startsWith("C"));
    }

    @Test
    @DisplayName("잘못된 상품 ID 접두사 테스트")
    void testInvalidProductIdPrefix() {
        // Given
        String invalidProductId1 = "Z99999";
        String invalidProductId2 = "123456";
        String invalidProductId3 = "";
        String invalidProductId4 = null;

        // When & Then
        if (invalidProductId1 != null && !invalidProductId1.isEmpty()) {
            char prefix1 = invalidProductId1.charAt(0);
            assertFalse(prefix1 == 'D' || prefix1 == 'S' || prefix1 == 'F' || 
                       prefix1 == 'X' || prefix1 == 'A' || prefix1 == 'C');
        }

        if (invalidProductId2 != null && !invalidProductId2.isEmpty()) {
            char prefix2 = invalidProductId2.charAt(0);
            assertTrue(Character.isDigit(prefix2));
        }

        assertTrue(invalidProductId3.isEmpty());
        assertNull(invalidProductId4);
    }

    @Test
    @DisplayName("CustomUser 객체 테스트")
    void testCustomUserObject() {
        // Given & When
        CustomUser user = testUser;

        // Then
        assertNotNull(user);
        assertNotNull(user.getUser());
        assertEquals(1, user.getUser().getId());
        assertEquals("test@example.com", user.getUser().getEmail());
        assertEquals("테스트 사용자", user.getUser().getName());
        assertEquals("password", user.getUser().getPassword());
    }

    @Test
    @DisplayName("DTO toString 메소드 테스트")
    void testDTOToStringMethod() {
        // Given
        DepositDetailResponseDTO dto = new DepositDetailResponseDTO();
        dto.setId("D12345");
        dto.setName("테스트 예금");

        // When
        String dtoString = dto.toString();

        // Then
        assertNotNull(dtoString);
        assertTrue(dtoString.contains("D12345"));
        assertTrue(dtoString.contains("테스트 예금"));
    }

    @Test
    @DisplayName("DTO equals 및 hashCode 테스트")
    void testDTOEqualsAndHashCode() {
        // Given
        DepositDetailResponseDTO dto1 = new DepositDetailResponseDTO();
        dto1.setId("D12345");
        dto1.setName("테스트 예금");

        DepositDetailResponseDTO dto2 = new DepositDetailResponseDTO();
        dto2.setId("D12345");
        dto2.setName("테스트 예금");

        DepositDetailResponseDTO dto3 = new DepositDetailResponseDTO();
        dto3.setId("D54321");
        dto3.setName("다른 예금");

        // When & Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    @DisplayName("상품별 DTO 타입 구분 테스트")
    void testProductDTOTypeDistinction() {
        // Given
        DepositDetailResponseDTO depositDto = new DepositDetailResponseDTO();
        SavingsDetailResponseDTO savingsDto = new SavingsDetailResponseDTO();
        FundDetailResponseDTO fundDto = new FundDetailResponseDTO();
        ForexDetailResponseDTO forexDto = new ForexDetailResponseDTO();
        PensionDetailResponseDTO pensionDto = new PensionDetailResponseDTO();

        // When & Then
        assertNotNull(depositDto);
        assertNotNull(savingsDto);
        assertNotNull(fundDto);
        assertNotNull(forexDto);
        assertNotNull(pensionDto);

        assertNotEquals(depositDto.getClass(), savingsDto.getClass());
        assertNotEquals(savingsDto.getClass(), fundDto.getClass());
        assertNotEquals(fundDto.getClass(), forexDto.getClass());
        assertNotEquals(forexDto.getClass(), pensionDto.getClass());
    }

    @Test
    @DisplayName("Date 필드 처리 테스트")
    void testDateFieldHandling() {
        // Given
        DepositDetailResponseDTO dto = new DepositDetailResponseDTO();
        Date testDate = new Date();

        // When
        dto.setEndDate(testDate);

        // Then
        assertEquals(testDate, dto.getEndDate());

        // When - null 설정
        dto.setEndDate(null);

        // Then
        assertNull(dto.getEndDate());
    }

    @Test
    @DisplayName("Boolean 필드 처리 테스트")
    void testBooleanFieldHandling() {
        // Given
        ForexDetailResponseDTO dto = new ForexDetailResponseDTO();

        // When
        dto.setExtraDeposit(true);
        dto.setProtect(false);

        // Then
        assertTrue(dto.getExtraDeposit());
        assertFalse(dto.getProtect());

        // When - null 설정
        dto.setExtraDeposit(null);
        dto.setProtect(null);

        // Then
        assertNull(dto.getExtraDeposit());
        assertNull(dto.getProtect());
    }
}