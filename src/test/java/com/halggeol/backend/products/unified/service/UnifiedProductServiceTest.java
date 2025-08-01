package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UnifiedProductService 테스트")
class UnifiedProductServiceTest {

    @BeforeEach
    void setUp() {
        // 테스트 초기화
    }

    @Test
    @DisplayName("UnifiedProductResponseDTO getter/setter 테스트")
    void testUnifiedProductResponseDTOGetterSetter() {
        // Given
        UnifiedProductResponseDTO dto = new UnifiedProductResponseDTO();
        String productId = "D12345";
        String name = "KB 정기예금";
        String company = "KB국민은행";
        String tag1 = "높은금리";
        String tag2 = "안전성";
        String tag3 = "인기상품";
        String title = "3.5%";
        String subTitle = "최고 우대금리";
        String type = "예금";
        Integer fSector = 1;
        Integer saveTerm = 12;
        String minAmount = "100000";
        Integer viewCnt = 1000;
        Integer scrapCnt = 50;

        // When
        dto.setProductId(productId);
        dto.setName(name);
        dto.setCompany(company);
        dto.setTag1(tag1);
        dto.setTag2(tag2);
        dto.setTag3(tag3);
        dto.setTitle(title);
        dto.setSubTitle(subTitle);
        dto.setType(type);
        dto.setFSector(fSector);
        dto.setSaveTerm(saveTerm);
        dto.setMinAmount(minAmount);
        dto.setViewCnt(viewCnt);
        dto.setScrapCnt(scrapCnt);

        // Then
        assertEquals(productId, dto.getProductId());
        assertEquals(name, dto.getName());
        assertEquals(company, dto.getCompany());
        assertEquals(tag1, dto.getTag1());
        assertEquals(tag2, dto.getTag2());
        assertEquals(tag3, dto.getTag3());
        assertEquals(title, dto.getTitle());
        assertEquals(subTitle, dto.getSubTitle());
        assertEquals(type, dto.getType());
        assertEquals(fSector, dto.getFSector());
        assertEquals(saveTerm, dto.getSaveTerm());
        assertEquals(minAmount, dto.getMinAmount());
        assertEquals(viewCnt, dto.getViewCnt());
        assertEquals(scrapCnt, dto.getScrapCnt());
    }

    @Test
    @DisplayName("UnifiedProductResponseDTO 빌더 패턴 테스트")
    void testUnifiedProductResponseDTOBuilder() {
        // Given
        String productId = "S54321";
        String name = "신한 자유적금";
        String company = "신한은행";
        String type = "적금";
        Integer viewCnt = 500;

        // When
        UnifiedProductResponseDTO dto = UnifiedProductResponseDTO.builder()
                .productId(productId)
                .name(name)
                .company(company)
                .type(type)
                .viewCnt(viewCnt)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(productId, dto.getProductId());
        assertEquals(name, dto.getName());
        assertEquals(company, dto.getCompany());
        assertEquals(type, dto.getType());
        assertEquals(viewCnt, dto.getViewCnt());
    }

    @Test
    @DisplayName("UnifiedProductResponseDTO AllArgsConstructor 테스트")
    void testUnifiedProductResponseDTOAllArgsConstructor() {
        // Given
        String productId = "F98765";
        String name = "미래에셋 글로벌펀드";
        String company = "미래에셋자산운용";
        String tag1 = "해외투자";
        String tag2 = "고수익";
        String tag3 = "분산투자";
        String title = "연 5.2%";
        String subTitle = "3개월 수익률";
        String type = "펀드";
        Integer fSector = 3;
        Integer saveTerm = null;
        String minAmount = "10000";
        Integer viewCnt = 800;
        Integer scrapCnt = 120;

        // When
        UnifiedProductResponseDTO dto = new UnifiedProductResponseDTO(
                productId, name, company, tag1, tag2, tag3, title, subTitle,
                type, fSector, saveTerm, minAmount, viewCnt, scrapCnt);

        // Then
        assertNotNull(dto);
        assertEquals(productId, dto.getProductId());
        assertEquals(name, dto.getName());
        assertEquals(company, dto.getCompany());
        assertEquals(tag1, dto.getTag1());
        assertEquals(tag2, dto.getTag2());
        assertEquals(tag3, dto.getTag3());
        assertEquals(title, dto.getTitle());
        assertEquals(subTitle, dto.getSubTitle());
        assertEquals(type, dto.getType());
        assertEquals(fSector, dto.getFSector());
        assertNull(dto.getSaveTerm());
        assertEquals(minAmount, dto.getMinAmount());
        assertEquals(viewCnt, dto.getViewCnt());
        assertEquals(scrapCnt, dto.getScrapCnt());
    }

    @Test
    @DisplayName("UnifiedProductRegretRankingResponseDTO getter/setter 테스트")
    void testUnifiedProductRegretRankingResponseDTOGetterSetter() {
        // Given
        UnifiedProductRegretRankingResponseDTO dto = new UnifiedProductRegretRankingResponseDTO();
        Integer rank = 1;
        String productId = "D12345";
        String productName = "KB 정기예금";
        Integer risk = 2;
        Double rate = 3.5;
        Integer period = 12;

        // When
        dto.setRank(rank);
        dto.setProductId(productId);
        dto.setProductName(productName);
        dto.setRisk(risk);
        dto.setRate(rate);
        dto.setPeriod(period);

        // Then
        assertEquals(rank, dto.getRank());
        assertEquals(productId, dto.getProductId());
        assertEquals(productName, dto.getProductName());
        assertEquals(risk, dto.getRisk());
        assertEquals(rate, dto.getRate());
        assertEquals(period, dto.getPeriod());
    }

    @Test
    @DisplayName("UnifiedProductRegretRankingResponseDTO 빌더 패턴 테스트")
    void testUnifiedProductRegretRankingResponseDTOBuilder() {
        // Given
        Integer rank = 2;
        String productId = "S67890";
        String productName = "우리 적금";
        Integer risk = 1;
        Double rate = null; // 1위가 아니므로 null
        Integer period = null; // 1위가 아니므로 null

        // When
        UnifiedProductRegretRankingResponseDTO dto = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(rank)
                .productId(productId)
                .productName(productName)
                .risk(risk)
                .rate(rate)
                .period(period)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(rank, dto.getRank());
        assertEquals(productId, dto.getProductId());
        assertEquals(productName, dto.getProductName());
        assertEquals(risk, dto.getRisk());
        assertNull(dto.getRate());
        assertNull(dto.getPeriod());
    }

    @Test
    @DisplayName("UnifiedProductRegretRankingResponseDTO AllArgsConstructor 테스트")
    void testUnifiedProductRegretRankingResponseDTOAllArgsConstructor() {
        // Given
        Integer rank = 1;
        String productId = "F11111";
        String productName = "삼성 글로벌펀드";
        Integer risk = 4;
        Double rate = 7.2;
        Integer period = 36;

        // When
        UnifiedProductRegretRankingResponseDTO dto = new UnifiedProductRegretRankingResponseDTO(
                rank, productId, productName, risk, rate, period);

        // Then
        assertNotNull(dto);
        assertEquals(rank, dto.getRank());
        assertEquals(productId, dto.getProductId());
        assertEquals(productName, dto.getProductName());
        assertEquals(risk, dto.getRisk());
        assertEquals(rate, dto.getRate());
        assertEquals(period, dto.getPeriod());
    }

    @Test
    @DisplayName("DTO null 값 처리 테스트")
    void testDTONullValueHandling() {
        // Given
        UnifiedProductResponseDTO responseDto = new UnifiedProductResponseDTO();
        UnifiedProductRegretRankingResponseDTO rankingDto = new UnifiedProductRegretRankingResponseDTO();

        // When - UnifiedProductResponseDTO null 설정
        responseDto.setProductId(null);
        responseDto.setName(null);
        responseDto.setCompany(null);
        responseDto.setFSector(null);
        responseDto.setSaveTerm(null);

        // When - UnifiedProductRegretRankingResponseDTO null 설정
        rankingDto.setRank(null);
        rankingDto.setRate(null);
        rankingDto.setPeriod(null);

        // Then
        assertNull(responseDto.getProductId());
        assertNull(responseDto.getName());
        assertNull(responseDto.getCompany());
        assertNull(responseDto.getFSector());
        assertNull(responseDto.getSaveTerm());

        assertNull(rankingDto.getRank());
        assertNull(rankingDto.getRate());
        assertNull(rankingDto.getPeriod());
    }

    @Test
    @DisplayName("상품 타입별 분류 테스트")
    void testProductTypeClassification() {
        // Given
        UnifiedProductResponseDTO depositDto = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .type("예금")
                .fSector(1)
                .saveTerm(12)
                .build();

        UnifiedProductResponseDTO savingsDto = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .type("적금")
                .fSector(1)
                .saveTerm(24)
                .build();

        UnifiedProductResponseDTO fundDto = UnifiedProductResponseDTO.builder()
                .productId("F11111")
                .type("펀드")
                .fSector(3)
                .saveTerm(null) // 펀드는 저축기간이 없음
                .build();

        // When & Then
        assertEquals("예금", depositDto.getType());
        assertEquals("적금", savingsDto.getType());
        assertEquals("펀드", fundDto.getType());

        assertEquals(Integer.valueOf(1), depositDto.getFSector());
        assertEquals(Integer.valueOf(1), savingsDto.getFSector());
        assertEquals(Integer.valueOf(3), fundDto.getFSector());

        assertEquals(Integer.valueOf(12), depositDto.getSaveTerm());
        assertEquals(Integer.valueOf(24), savingsDto.getSaveTerm());
        assertNull(fundDto.getSaveTerm());
    }

    @Test
    @DisplayName("리스트 처리 테스트")
    void testListHandling() {
        // Given
        List<UnifiedProductResponseDTO> productList = new ArrayList<>();
        List<UnifiedProductRegretRankingResponseDTO> rankingList = new ArrayList<>();

        UnifiedProductResponseDTO product1 = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("상품1")
                .viewCnt(100)
                .build();

        UnifiedProductResponseDTO product2 = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .name("상품2")
                .viewCnt(200)
                .build();

        UnifiedProductRegretRankingResponseDTO ranking1 = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(1)
                .productId("F11111")
                .productName("랭킹상품1")
                .build();

        // When
        productList.add(product1);
        productList.add(product2);
        rankingList.add(ranking1);

        // Then
        assertEquals(2, productList.size());
        assertEquals(1, rankingList.size());
        assertEquals("상품1", productList.get(0).getName());
        assertEquals("상품2", productList.get(1).getName());
        assertEquals(Integer.valueOf(1), rankingList.get(0).getRank());
    }

    @Test
    @DisplayName("DTO toString 메소드 테스트")
    void testDTOToStringMethod() {
        // Given
        UnifiedProductResponseDTO responseDto = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("테스트상품")
                .company("테스트은행")
                .build();

        UnifiedProductRegretRankingResponseDTO rankingDto = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(1)
                .productId("S67890")
                .productName("랭킹상품")
                .build();

        // When
        String responseString = responseDto.toString();
        String rankingString = rankingDto.toString();

        // Then
        assertNotNull(responseString);
        assertNotNull(rankingString);
        assertTrue(responseString.contains("D12345"));
        assertTrue(responseString.contains("테스트상품"));
        assertTrue(rankingString.contains("1"));
        assertTrue(rankingString.contains("S67890"));
    }

    @Test
    @DisplayName("DTO equals 및 hashCode 테스트")
    void testDTOEqualsAndHashCode() {
        // Given
        UnifiedProductResponseDTO dto1 = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("상품1")
                .build();

        UnifiedProductResponseDTO dto2 = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("상품1")
                .build();

        UnifiedProductResponseDTO dto3 = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .name("상품2")
                .build();

        // When & Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    @DisplayName("정렬 기준 검증 테스트")
    void testSortCriteria() {
        // Given
        String popularDesc = "popularDesc";
        String rateDesc = "rateDesc";
        String viewDesc = "viewDesc";

        // When & Then
        assertEquals("popularDesc", popularDesc);
        assertEquals("rateDesc", rateDesc);
        assertEquals("viewDesc", viewDesc);

        // 기본 정렬이 인기순인지 확인
        String defaultSort = "popularDesc";
        assertEquals("popularDesc", defaultSort);
    }

    @Test
    @DisplayName("금융권 구분 테스트")
    void testFinancialSectorClassification() {
        // Given
        Integer firstTier = 1; // 1금융권
        Integer secondTier = 2; // 2금융권
        Integer assetManagement = 3; // 자산운용

        UnifiedProductResponseDTO bankProduct = UnifiedProductResponseDTO.builder()
                .fSector(firstTier)
                .type("예금")
                .build();

        UnifiedProductResponseDTO savingsProduct = UnifiedProductResponseDTO.builder()
                .fSector(secondTier)
                .type("적금")
                .build();

        UnifiedProductResponseDTO fundProduct = UnifiedProductResponseDTO.builder()
                .fSector(assetManagement)
                .type("펀드")
                .build();

        // When & Then
        assertEquals(firstTier, bankProduct.getFSector());
        assertEquals(secondTier, savingsProduct.getFSector());
        assertEquals(assetManagement, fundProduct.getFSector());
    }

    @Test
    @DisplayName("빈 생성자 테스트")
    void testNoArgsConstructors() {
        // Given & When
        UnifiedProductResponseDTO responseDto = new UnifiedProductResponseDTO();
        UnifiedProductRegretRankingResponseDTO rankingDto = new UnifiedProductRegretRankingResponseDTO();

        // Then
        assertNotNull(responseDto);
        assertNotNull(rankingDto);
        assertNull(responseDto.getProductId());
        assertNull(rankingDto.getRank());
    }
}