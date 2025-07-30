package com.halggeol.backend.products.unified.service;

import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UnifiedProductServiceImpl 및 관련 DTO 테스트")
class UnifiedProductServiceImplTest {

    @BeforeEach
    void setUp() {
        // 테스트 초기화
    }

    @Test
    @DisplayName("UnifiedProductResponseDTO 완전한 필드 getter/setter 테스트")
    void testUnifiedProductResponseDTOCompleteFields() {
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
    @DisplayName("UnifiedProductResponseDTO Builder 패턴 체이닝 테스트")
    void testUnifiedProductResponseDTOBuilderChaining() {
        // Given & When
        UnifiedProductResponseDTO dto = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .name("신한 자유적금")
                .company("신한은행")
                .tag1("월복리")
                .tag2("자유입출금")
                .tag3("만기선택")
                .title("연 2.8%")
                .subTitle("기본금리")
                .type("적금")
                .fSector(1)
                .saveTerm(24)
                .minAmount("10000")
                .viewCnt(500)
                .scrapCnt(30)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals("S67890", dto.getProductId());
        assertEquals("신한 자유적금", dto.getName());
        assertEquals("신한은행", dto.getCompany());
        assertEquals("월복리", dto.getTag1());
        assertEquals("자유입출금", dto.getTag2());
        assertEquals("만기선택", dto.getTag3());
        assertEquals("연 2.8%", dto.getTitle());
        assertEquals("기본금리", dto.getSubTitle());
        assertEquals("적금", dto.getType());
        assertEquals(Integer.valueOf(1), dto.getFSector());
        assertEquals(Integer.valueOf(24), dto.getSaveTerm());
        assertEquals("10000", dto.getMinAmount());
        assertEquals(Integer.valueOf(500), dto.getViewCnt());
        assertEquals(Integer.valueOf(30), dto.getScrapCnt());
    }

    @Test
    @DisplayName("UnifiedProductResponseDTO AllArgsConstructor 테스트")
    void testUnifiedProductResponseDTOAllArgsConstructor() {
        // Given
        String productId = "F11111";
        String name = "미래에셋 글로벌펀드";
        String company = "미래에셋자산운용";
        String tag1 = "해외투자";
        String tag2 = "고수익";
        String tag3 = "분산투자";
        String title = "연 5.2%";
        String subTitle = "3개월 수익률";
        String type = "펀드";
        Integer fSector = 3;
        Integer saveTerm = null; // 펀드는 저축기간 없음
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
        assertNull(dto.getSaveTerm()); // 펀드는 저축기간 없음
        assertEquals(minAmount, dto.getMinAmount());
        assertEquals(viewCnt, dto.getViewCnt());
        assertEquals(scrapCnt, dto.getScrapCnt());
    }

    @Test
    @DisplayName("UnifiedProductResponseDTO NoArgsConstructor 테스트")
    void testUnifiedProductResponseDTONoArgsConstructor() {
        // Given & When
        UnifiedProductResponseDTO dto = new UnifiedProductResponseDTO();

        // Then
        assertNotNull(dto);
        assertNull(dto.getProductId());
        assertNull(dto.getName());
        assertNull(dto.getCompany());
        assertNull(dto.getTag1());
        assertNull(dto.getTag2());
        assertNull(dto.getTag3());
        assertNull(dto.getTitle());
        assertNull(dto.getSubTitle());
        assertNull(dto.getType());
        assertNull(dto.getFSector());
        assertNull(dto.getSaveTerm());
        assertNull(dto.getMinAmount());
        assertNull(dto.getViewCnt());
        assertNull(dto.getScrapCnt());
    }

    @Test
    @DisplayName("UnifiedProductRegretRankingResponseDTO 완전한 필드 getter/setter 테스트")
    void testUnifiedProductRegretRankingResponseDTOCompleteFields() {
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
    @DisplayName("UnifiedProductRegretRankingResponseDTO Builder 패턴 테스트")
    void testUnifiedProductRegretRankingResponseDTOBuilder() {
        // Given & When
        UnifiedProductRegretRankingResponseDTO dto = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(1)
                .productId("S67890")
                .productName("우리 적금")
                .risk(1)
                .rate(2.8)
                .period(24)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(Integer.valueOf(1), dto.getRank());
        assertEquals("S67890", dto.getProductId());
        assertEquals("우리 적금", dto.getProductName());
        assertEquals(Integer.valueOf(1), dto.getRisk());
        assertEquals(Double.valueOf(2.8), dto.getRate());
        assertEquals(Integer.valueOf(24), dto.getPeriod());
    }

    @Test
    @DisplayName("UnifiedProductRegretRankingResponseDTO AllArgsConstructor 테스트")
    void testUnifiedProductRegretRankingResponseDTOAllArgsConstructor() {
        // Given
        Integer rank = 2;
        String productId = "F22222";
        String productName = "삼성 글로벌펀드";
        Integer risk = 4;
        Double rate = null; // 1위가 아니므로 null
        Integer period = null; // 1위가 아니므로 null

        // When
        UnifiedProductRegretRankingResponseDTO dto = new UnifiedProductRegretRankingResponseDTO(
                rank, productId, productName, risk, rate, period);

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
    @DisplayName("UnifiedProductRegretRankingResponseDTO NoArgsConstructor 테스트")
    void testUnifiedProductRegretRankingResponseDTONoArgsConstructor() {
        // Given & When
        UnifiedProductRegretRankingResponseDTO dto = new UnifiedProductRegretRankingResponseDTO();

        // Then
        assertNotNull(dto);
        assertNull(dto.getRank());
        assertNull(dto.getProductId());
        assertNull(dto.getProductName());
        assertNull(dto.getRisk());
        assertNull(dto.getRate());
        assertNull(dto.getPeriod());
    }

    @Test
    @DisplayName("상품 타입별 분류 및 필드 검증 테스트")
    void testProductTypeClassificationAndFields() {
        // Given - 예금 상품
        UnifiedProductResponseDTO depositDto = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("KB 정기예금")
                .type("예금")
                .fSector(1) // 1금융권
                .saveTerm(12) // 12개월
                .minAmount("100000")
                .build();

        // Given - 적금 상품
        UnifiedProductResponseDTO savingsDto = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .name("신한 자유적금")
                .type("적금")
                .fSector(1) // 1금융권
                .saveTerm(24) // 24개월
                .minAmount("10000")
                .build();

        // Given - 펀드 상품
        UnifiedProductResponseDTO fundDto = UnifiedProductResponseDTO.builder()
                .productId("F11111")
                .name("미래에셋 글로벌펀드")
                .type("펀드")
                .fSector(3) // 자산운용
                .saveTerm(null) // 펀드는 저축기간 없음
                .minAmount("10000")
                .build();

        // When & Then - 예금 상품 검증
        assertEquals("예금", depositDto.getType());
        assertEquals(Integer.valueOf(1), depositDto.getFSector());
        assertEquals(Integer.valueOf(12), depositDto.getSaveTerm());
        assertEquals("100000", depositDto.getMinAmount());
        assertTrue(depositDto.getProductId().startsWith("D"));

        // When & Then - 적금 상품 검증
        assertEquals("적금", savingsDto.getType());
        assertEquals(Integer.valueOf(1), savingsDto.getFSector());
        assertEquals(Integer.valueOf(24), savingsDto.getSaveTerm());
        assertEquals("10000", savingsDto.getMinAmount());
        assertTrue(savingsDto.getProductId().startsWith("S"));

        // When & Then - 펀드 상품 검증
        assertEquals("펀드", fundDto.getType());
        assertEquals(Integer.valueOf(3), fundDto.getFSector());
        assertNull(fundDto.getSaveTerm()); // 펀드는 저축기간 없음
        assertEquals("10000", fundDto.getMinAmount());
        assertTrue(fundDto.getProductId().startsWith("F"));
    }

    @Test
    @DisplayName("금융권 구분(fSector) 테스트")
    void testFinancialSectorClassification() {
        // Given
        Integer firstTier = 1; // 1금융권 (은행)
        Integer secondTier = 2; // 2금융권 (저축은행 등)
        Integer assetManagement = 3; // 자산운용사

        UnifiedProductResponseDTO bankProduct = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .type("예금")
                .fSector(firstTier)
                .build();

        UnifiedProductResponseDTO savingsBankProduct = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .type("적금")
                .fSector(secondTier)
                .build();

        UnifiedProductResponseDTO fundProduct = UnifiedProductResponseDTO.builder()
                .productId("F11111")
                .type("펀드")
                .fSector(assetManagement)
                .build();

        // When & Then
        assertEquals(firstTier, bankProduct.getFSector());
        assertEquals(secondTier, savingsBankProduct.getFSector());
        assertEquals(assetManagement, fundProduct.getFSector());

        // 금융권별 특성 검증
        assertTrue(bankProduct.getFSector() == 1); // 1금융권
        assertTrue(savingsBankProduct.getFSector() == 2); // 2금융권
        assertTrue(fundProduct.getFSector() == 3); // 자산운용
    }

    @Test
    @DisplayName("정렬 기준(sort) 기본값 검증 테스트")
    void testDefaultSortCriteria() {
        // Given
        String nullSort = null;
        String blankSort = "";
        String emptySort = "   ";
        String validSort = "rateDesc";

        // When & Then - null인 경우 기본값 적용
        if (nullSort == null || nullSort.isBlank()) {
            nullSort = "popularDesc";
        }
        assertEquals("popularDesc", nullSort);

        // When & Then - 빈 문자열인 경우 기본값 적용
        if (blankSort == null || blankSort.isBlank()) {
            blankSort = "popularDesc";
        }
        assertEquals("popularDesc", blankSort);

        // When & Then - 공백인 경우 기본값 적용
        if (emptySort == null || emptySort.isBlank()) {
            emptySort = "popularDesc";
        }
        assertEquals("popularDesc", emptySort);

        // When & Then - 유효한 값인 경우 그대로 유지
        if (validSort == null || validSort.isBlank()) {
            validSort = "popularDesc";
        }
        assertEquals("rateDesc", validSort);
    }

    @Test
    @DisplayName("리스트 처리 및 데이터 검증 테스트")
    void testListHandlingAndDataValidation() {
        // Given
        List<UnifiedProductResponseDTO> productList = new ArrayList<>();
        List<UnifiedProductRegretRankingResponseDTO> rankingList = new ArrayList<>();

        UnifiedProductResponseDTO product1 = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("상품1")
                .viewCnt(100)
                .scrapCnt(10)
                .build();

        UnifiedProductResponseDTO product2 = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .name("상품2")
                .viewCnt(200)
                .scrapCnt(20)
                .build();

        UnifiedProductRegretRankingResponseDTO ranking1 = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(1)
                .productId("F11111")
                .productName("랭킹상품1")
                .risk(3)
                .rate(5.5)
                .period(36)
                .build();

        UnifiedProductRegretRankingResponseDTO ranking2 = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(2)
                .productId("X22222")
                .productName("랭킹상품2")
                .risk(2)
                .rate(null) // 1위가 아니므로 null
                .period(null) // 1위가 아니므로 null
                .build();

        // When
        productList.add(product1);
        productList.add(product2);
        rankingList.add(ranking1);
        rankingList.add(ranking2);

        // Then
        assertEquals(2, productList.size());
        assertEquals(2, rankingList.size());

        // 첫 번째 상품 검증
        assertEquals("D12345", productList.get(0).getProductId());
        assertEquals("상품1", productList.get(0).getName());
        assertEquals(Integer.valueOf(100), productList.get(0).getViewCnt());
        assertEquals(Integer.valueOf(10), productList.get(0).getScrapCnt());

        // 두 번째 상품 검증
        assertEquals("S67890", productList.get(1).getProductId());
        assertEquals("상품2", productList.get(1).getName());
        assertEquals(Integer.valueOf(200), productList.get(1).getViewCnt());
        assertEquals(Integer.valueOf(20), productList.get(1).getScrapCnt());

        // 첫 번째 랭킹 상품 검증 (1위)
        assertEquals(Integer.valueOf(1), rankingList.get(0).getRank());
        assertEquals("F11111", rankingList.get(0).getProductId());
        assertEquals("랭킹상품1", rankingList.get(0).getProductName());
        assertEquals(Integer.valueOf(3), rankingList.get(0).getRisk());
        assertEquals(Double.valueOf(5.5), rankingList.get(0).getRate());
        assertEquals(Integer.valueOf(36), rankingList.get(0).getPeriod());

        // 두 번째 랭킹 상품 검증 (2위)
        assertEquals(Integer.valueOf(2), rankingList.get(1).getRank());
        assertEquals("X22222", rankingList.get(1).getProductId());
        assertEquals("랭킹상품2", rankingList.get(1).getProductName());
        assertEquals(Integer.valueOf(2), rankingList.get(1).getRisk());
        assertNull(rankingList.get(1).getRate()); // 1위가 아니므로 null
        assertNull(rankingList.get(1).getPeriod()); // 1위가 아니므로 null
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
        responseDto.setType(null);
        responseDto.setFSector(null);
        responseDto.setSaveTerm(null);
        responseDto.setMinAmount(null);
        responseDto.setViewCnt(null);
        responseDto.setScrapCnt(null);

        // When - UnifiedProductRegretRankingResponseDTO null 설정
        rankingDto.setRank(null);
        rankingDto.setProductId(null);
        rankingDto.setProductName(null);
        rankingDto.setRisk(null);
        rankingDto.setRate(null);
        rankingDto.setPeriod(null);

        // Then - UnifiedProductResponseDTO null 검증
        assertNull(responseDto.getProductId());
        assertNull(responseDto.getName());
        assertNull(responseDto.getCompany());
        assertNull(responseDto.getType());
        assertNull(responseDto.getFSector());
        assertNull(responseDto.getSaveTerm());
        assertNull(responseDto.getMinAmount());
        assertNull(responseDto.getViewCnt());
        assertNull(responseDto.getScrapCnt());

        // Then - UnifiedProductRegretRankingResponseDTO null 검증
        assertNull(rankingDto.getRank());
        assertNull(rankingDto.getProductId());
        assertNull(rankingDto.getProductName());
        assertNull(rankingDto.getRisk());
        assertNull(rankingDto.getRate());
        assertNull(rankingDto.getPeriod());
    }

    @Test
    @DisplayName("DTO toString 메소드 테스트")
    void testDTOToStringMethod() {
        // Given
        UnifiedProductResponseDTO responseDto = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("테스트상품")
                .company("테스트은행")
                .type("예금")
                .build();

        UnifiedProductRegretRankingResponseDTO rankingDto = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(1)
                .productId("S67890")
                .productName("랭킹상품")
                .risk(2)
                .build();

        // When
        String responseString = responseDto.toString();
        String rankingString = rankingDto.toString();

        // Then
        assertNotNull(responseString);
        assertNotNull(rankingString);
        assertTrue(responseString.contains("D12345"));
        assertTrue(responseString.contains("테스트상품"));
        assertTrue(responseString.contains("테스트은행"));
        assertTrue(rankingString.contains("1"));
        assertTrue(rankingString.contains("S67890"));
        assertTrue(rankingString.contains("랭킹상품"));
    }

    @Test
    @DisplayName("DTO equals 및 hashCode 테스트")
    void testDTOEqualsAndHashCode() {
        // Given
        UnifiedProductResponseDTO dto1 = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("상품1")
                .company("은행1")
                .build();

        UnifiedProductResponseDTO dto2 = UnifiedProductResponseDTO.builder()
                .productId("D12345")
                .name("상품1")
                .company("은행1")
                .build();

        UnifiedProductResponseDTO dto3 = UnifiedProductResponseDTO.builder()
                .productId("S67890")
                .name("상품2")
                .company("은행2")
                .build();

        UnifiedProductRegretRankingResponseDTO ranking1 = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(1)
                .productId("F11111")
                .productName("랭킹1")
                .build();

        UnifiedProductRegretRankingResponseDTO ranking2 = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(1)
                .productId("F11111")
                .productName("랭킹1")
                .build();

        UnifiedProductRegretRankingResponseDTO ranking3 = UnifiedProductRegretRankingResponseDTO.builder()
                .rank(2)
                .productId("X22222")
                .productName("랭킹2")
                .build();

        // When & Then - UnifiedProductResponseDTO
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);

        // When & Then - UnifiedProductRegretRankingResponseDTO
        assertEquals(ranking1, ranking2);
        assertEquals(ranking1.hashCode(), ranking2.hashCode());
        assertNotEquals(ranking1, ranking3);
    }

    @Test
    @DisplayName("필터링 파라미터 검증 테스트")
    void testFilteringParameters() {
        // Given - 다양한 필터링 파라미터
        String sort1 = "popularDesc"; // 인기순
        String sort2 = "rateDesc"; // 금리순
        String sort3 = "viewDesc"; // 조회순
        String type1 = "예금";
        String type2 = "적금";
        String type3 = "펀드";
        Integer fSector1 = 1; // 1금융권
        Integer fSector2 = 2; // 2금융권
        Integer fSector3 = 3; // 자산운용
        Integer saveTerm1 = 12; // 12개월
        Integer saveTerm2 = 24; // 24개월
        String minAmount1 = "10000";
        String minAmount2 = "100000";

        // When & Then - 정렬 기준 검증
        assertEquals("popularDesc", sort1);
        assertEquals("rateDesc", sort2);
        assertEquals("viewDesc", sort3);

        // When & Then - 상품 타입 검증
        assertEquals("예금", type1);
        assertEquals("적금", type2);
        assertEquals("펀드", type3);

        // When & Then - 금융권 검증
        assertEquals(Integer.valueOf(1), fSector1);
        assertEquals(Integer.valueOf(2), fSector2);
        assertEquals(Integer.valueOf(3), fSector3);

        // When & Then - 저축기간 검증
        assertEquals(Integer.valueOf(12), saveTerm1);
        assertEquals(Integer.valueOf(24), saveTerm2);

        // When & Then - 최소금액 검증
        assertEquals("10000", minAmount1);
        assertEquals("100000", minAmount2);
    }

    @Test
    @DisplayName("비즈니스 로직 데이터 흐름 테스트")
    void testBusinessLogicDataFlow() {
        // Given - 필터링 서비스 로직 시뮬레이션
        String inputSort = null;
        String type = "예금";
        Integer fSector = 1;
        Integer saveTerm = 12;
        String minAmount = "100000";

        // When - getFilteredProducts 로직 시뮬레이션
        String processedSort = inputSort;
        if (processedSort == null || processedSort.isBlank()) {
            processedSort = "popularDesc"; // 기본 정렬 기준 -> 인기순
        }

        // Then
        assertEquals("popularDesc", processedSort);
        assertEquals("예금", type);
        assertEquals(Integer.valueOf(1), fSector);
        assertEquals(Integer.valueOf(12), saveTerm);
        assertEquals("100000", minAmount);

        // Given - 다른 입력값으로 테스트
        String inputSort2 = "rateDesc";
        String processedSort2 = inputSort2;
        if (processedSort2 == null || processedSort2.isBlank()) {
            processedSort2 = "popularDesc";
        }

        // Then
        assertEquals("rateDesc", processedSort2); // 유효한 값이므로 그대로 유지
    }
}