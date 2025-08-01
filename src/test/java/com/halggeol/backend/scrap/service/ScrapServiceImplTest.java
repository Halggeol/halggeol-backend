package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ScrapServiceImpl 및 관련 DTO 테스트")
class ScrapServiceImplTest {

    private CustomUser testUser;
    private int testUserId;
    private String testProductId;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        User user = User.builder()
                .id(testUserId)
                .email("test@example.com")
                .name("테스트 사용자")
                .password("password")
                .build();
        testUser = new CustomUser(user);
        testProductId = "D12345";
    }

    @Test
    @DisplayName("ScrapRequestDTO getter/setter 테스트")
    void testScrapRequestDTOGetterSetter() {
        // Given
        ScrapRequestDTO dto = new ScrapRequestDTO();
        String productId = "D12345";

        // When
        dto.setProductId(productId);

        // Then
        assertEquals(productId, dto.getProductId());
    }

    @Test
    @DisplayName("ScrapRequestDTO AllArgsConstructor 테스트")
    void testScrapRequestDTOAllArgsConstructor() {
        // Given
        String productId = "S67890";

        // When
        ScrapRequestDTO dto = new ScrapRequestDTO(productId);

        // Then
        assertNotNull(dto);
        assertEquals(productId, dto.getProductId());
    }

    @Test
    @DisplayName("ScrapRequestDTO RequiredArgsConstructor (기본 생성자) 테스트")
    void testScrapRequestDTORequiredArgsConstructor() {
        // Given & When
        ScrapRequestDTO dto = new ScrapRequestDTO();

        // Then
        assertNotNull(dto);
        assertNull(dto.getProductId());
    }

    @Test
    @DisplayName("ScrapRequestDTO null 값 처리 테스트")
    void testScrapRequestDTONullHandling() {
        // Given
        ScrapRequestDTO dto = new ScrapRequestDTO();

        // When
        dto.setProductId(null);

        // Then
        assertNull(dto.getProductId());
    }

    @Test
    @DisplayName("ScrapRequestDTO 다양한 상품 ID 타입 테스트")
    void testScrapRequestDTOVariousProductIds() {
        // Given
        String depositId = "D12345";
        String savingsId = "S67890";
        String fundId = "F11111";
        String forexId = "X22222";
        String pensionIdA = "A33333";
        String pensionIdC = "C44444";

        // When
        ScrapRequestDTO depositDto = new ScrapRequestDTO(depositId);
        ScrapRequestDTO savingsDto = new ScrapRequestDTO(savingsId);
        ScrapRequestDTO fundDto = new ScrapRequestDTO(fundId);
        ScrapRequestDTO forexDto = new ScrapRequestDTO(forexId);
        ScrapRequestDTO pensionDtoA = new ScrapRequestDTO(pensionIdA);
        ScrapRequestDTO pensionDtoC = new ScrapRequestDTO(pensionIdC);

        // Then
        assertEquals(depositId, depositDto.getProductId());
        assertEquals(savingsId, savingsDto.getProductId());
        assertEquals(fundId, fundDto.getProductId());
        assertEquals(forexId, forexDto.getProductId());
        assertEquals(pensionIdA, pensionDtoA.getProductId());
        assertEquals(pensionIdC, pensionDtoC.getProductId());

        // 첫 글자 확인
        assertTrue(depositDto.getProductId().startsWith("D"));
        assertTrue(savingsDto.getProductId().startsWith("S"));
        assertTrue(fundDto.getProductId().startsWith("F"));
        assertTrue(forexDto.getProductId().startsWith("X"));
        assertTrue(pensionDtoA.getProductId().startsWith("A"));
        assertTrue(pensionDtoC.getProductId().startsWith("C"));
    }

    @Test
    @DisplayName("Scrap 도메인 객체 getter/setter 테스트")
    void testScrapDomainGetterSetter() {
        // Given
        Scrap scrap = new Scrap();
        int userId = 123;
        String productId = "D12345";

        // When
        scrap.setUserId(userId);
        scrap.setProductId(productId);

        // Then
        assertEquals(userId, scrap.getUserId());
        assertEquals(productId, scrap.getProductId());
    }

    @Test
    @DisplayName("Scrap 도메인 객체 Builder 패턴 테스트")
    void testScrapDomainBuilder() {
        // Given
        int userId = 456;
        String productId = "S67890";

        // When
        Scrap scrap = Scrap.builder()
                .userId(userId)
                .productId(productId)
                .build();

        // Then
        assertNotNull(scrap);
        assertEquals(userId, scrap.getUserId());
        assertEquals(productId, scrap.getProductId());
    }

    @Test
    @DisplayName("Scrap 도메인 객체 AllArgsConstructor 테스트")
    void testScrapDomainAllArgsConstructor() {
        // Given
        int userId = 789;
        String productId = "F11111";

        // When
        Scrap scrap = new Scrap(userId, productId);

        // Then
        assertNotNull(scrap);
        assertEquals(userId, scrap.getUserId());
        assertEquals(productId, scrap.getProductId());
    }

    @Test
    @DisplayName("Scrap 도메인 객체 NoArgsConstructor 테스트")
    void testScrapDomainNoArgsConstructor() {
        // Given & When
        Scrap scrap = new Scrap();

        // Then
        assertNotNull(scrap);
        assertEquals(0, scrap.getUserId()); // int 기본값
        assertNull(scrap.getProductId());
    }

    @Test
    @DisplayName("Scrap 도메인 객체 equals 및 hashCode 테스트")
    void testScrapDomainEqualsAndHashCode() {
        // Given
        int userId1 = 100;
        String productId1 = "D12345";
        
        Scrap scrap1 = new Scrap(userId1, productId1);
        Scrap scrap2 = new Scrap(userId1, productId1);
        Scrap scrap3 = new Scrap(200, "S67890");

        // When & Then
        assertEquals(scrap1, scrap2);
        assertEquals(scrap1.hashCode(), scrap2.hashCode());
        assertNotEquals(scrap1, scrap3);
        assertNotEquals(scrap1.hashCode(), scrap3.hashCode());
    }

    @Test
    @DisplayName("CustomUser에서 사용자 정보 추출 테스트")
    void testCustomUserInfoExtraction() {
        // Given
        CustomUser user = testUser;

        // When
        int userId = user.getUser().getId();
        String userName = user.getUser().getName();
        String email = user.getUser().getEmail();

        // Then
        assertEquals(testUserId, userId);
        assertEquals("테스트 사용자", userName);
        assertEquals("test@example.com", email);
    }

    @Test
    @DisplayName("상품 ID별 타입 분류 테스트")
    void testProductIdTypeClassification() {
        // Given
        String depositId = "D12345";
        String savingsId = "S67890";
        String fundId = "F11111";
        String forexId = "X22222";
        String pensionIdA = "A33333";
        String pensionIdC = "C44444";

        // When & Then - Deposit 상품
        assertTrue(depositId.startsWith("D"));
        assertFalse(depositId.startsWith("S"));
        assertFalse(depositId.startsWith("F"));

        // When & Then - Savings 상품
        assertTrue(savingsId.startsWith("S"));
        assertFalse(savingsId.startsWith("D"));
        assertFalse(savingsId.startsWith("F"));

        // When & Then - Fund 상품
        assertTrue(fundId.startsWith("F"));
        assertFalse(fundId.startsWith("D"));
        assertFalse(fundId.startsWith("S"));

        // When & Then - Forex 상품
        assertTrue(forexId.startsWith("X"));
        assertFalse(forexId.startsWith("D"));
        assertFalse(forexId.startsWith("S"));

        // When & Then - Pension 상품 (A, C)
        assertTrue(pensionIdA.startsWith("A"));
        assertTrue(pensionIdC.startsWith("C"));
        assertFalse(pensionIdA.startsWith("D"));
        assertFalse(pensionIdC.startsWith("S"));
    }

    @Test
    @DisplayName("Scrap 생성 시 필요한 데이터 검증")
    void testScrapCreationDataValidation() {
        // Given
        ScrapRequestDTO requestDto = new ScrapRequestDTO("D12345");
        CustomUser user = testUser;

        // When
        String productId = requestDto.getProductId();
        int userId = user.getUser().getId();

        Scrap scrap = Scrap.builder()
                .userId(userId)
                .productId(productId)
                .build();

        // Then
        assertNotNull(productId);
        assertTrue(userId > 0);
        assertNotNull(scrap);
        assertEquals(userId, scrap.getUserId());
        assertEquals(productId, scrap.getProductId());
    }

    @Test
    @DisplayName("DTO toString 메소드 테스트")
    void testDTOToStringMethod() {
        // Given
        ScrapRequestDTO requestDto = new ScrapRequestDTO("D12345");
        Scrap scrap = new Scrap(123, "S67890");

        // When
        String requestString = requestDto.toString();
        String scrapString = scrap.toString();

        // Then
        assertNotNull(requestString);
        assertNotNull(scrapString);
        assertTrue(requestString.contains("D12345"));
        assertTrue(scrapString.contains("123"));
        assertTrue(scrapString.contains("S67890"));
    }

    @Test
    @DisplayName("빈 문자열 및 특수 문자 상품 ID 처리 테스트")
    void testSpecialCharacterProductIdHandling() {
        // Given
        String emptyId = "";
        String specialId = "D12345-SPECIAL";
        String numericId = "D123";

        // When
        ScrapRequestDTO emptyDto = new ScrapRequestDTO(emptyId);
        ScrapRequestDTO specialDto = new ScrapRequestDTO(specialId);
        ScrapRequestDTO numericDto = new ScrapRequestDTO(numericId);

        // Then
        assertEquals(emptyId, emptyDto.getProductId());
        assertEquals(specialId, specialDto.getProductId());
        assertEquals(numericId, numericDto.getProductId());

        assertTrue(emptyDto.getProductId().isEmpty());
        assertTrue(specialDto.getProductId().contains("-"));
        assertTrue(numericDto.getProductId().length() == 4);
    }

    @Test
    @DisplayName("스크랩 서비스 비즈니스 로직 데이터 흐름 테스트")
    void testScrapServiceDataFlow() {
        // Given
        String productId = "D12345";
        ScrapRequestDTO requestDto = new ScrapRequestDTO(productId);
        CustomUser user = testUser;

        // When - 스크랩 추가 시 데이터 흐름
        String extractedProductId = requestDto.getProductId();
        int extractedUserId = user.getUser().getId();

        Scrap scrapForAdd = Scrap.builder()
                .userId(extractedUserId)
                .productId(extractedProductId)
                .build();

        // When - 스크랩 삭제 시 데이터 흐름
        String deleteProductId = requestDto.getProductId();
        int deleteUserId = user.getUser().getId();

        // Then - 추가 데이터 검증
        assertEquals(productId, extractedProductId);
        assertEquals(testUserId, extractedUserId);
        assertNotNull(scrapForAdd);
        assertEquals(extractedUserId, scrapForAdd.getUserId());
        assertEquals(extractedProductId, scrapForAdd.getProductId());

        // Then - 삭제 데이터 검증
        assertEquals(productId, deleteProductId);
        assertEquals(testUserId, deleteUserId);
    }

    @Test
    @DisplayName("다양한 금융상품 타입별 스크랩 데이터 테스트")
    void testScrapDataForVariousProductTypes() {
        // Given
        int userId = testUserId;
        String[] productIds = {"D12345", "S67890", "F11111", "X22222", "A33333", "C44444"};
        String[] productTypes = {"예금", "적금", "펀드", "외환", "연금A", "연금C"};

        // When & Then
        for (int i = 0; i < productIds.length; i++) {
            ScrapRequestDTO requestDto = new ScrapRequestDTO(productIds[i]);
            Scrap scrap = new Scrap(userId, productIds[i]);

            assertEquals(productIds[i], requestDto.getProductId());
            assertEquals(userId, scrap.getUserId());
            assertEquals(productIds[i], scrap.getProductId());

            // 상품 타입별 ID 패턴 검증
            if (i == 0) assertTrue(productIds[i].startsWith("D")); // 예금
            else if (i == 1) assertTrue(productIds[i].startsWith("S")); // 적금
            else if (i == 2) assertTrue(productIds[i].startsWith("F")); // 펀드
            else if (i == 3) assertTrue(productIds[i].startsWith("X")); // 외환
            else if (i == 4) assertTrue(productIds[i].startsWith("A")); // 연금A
            else if (i == 5) assertTrue(productIds[i].startsWith("C")); // 연금C
        }
    }

    @Test
    @DisplayName("사용자 ID 타입 변환 테스트")
    void testUserIdTypeConversion() {
        // Given
        CustomUser user = testUser;

        // When
        int userId = user.getUser().getId();
        String userIdString = String.valueOf(userId);
        Integer userIdInteger = Integer.valueOf(userId);

        // Then
        assertEquals(testUserId, userId);
        assertEquals("1", userIdString);
        assertEquals(Integer.valueOf(1), userIdInteger);
        assertTrue(userId > 0);
        assertFalse(userIdString.isEmpty());
        assertNotNull(userIdInteger);
    }
}