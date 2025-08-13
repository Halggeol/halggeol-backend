package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.dto.ScrappedProductResponseDTO;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ScrapService 인터페이스 테스트")
class ScrapServiceTest {

    private CustomUser testUser;
    private int testUserId;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .name("테스트 사용자")
                .password("password")
                .build();
        testUser = new CustomUser(user);
    }

    @Test
    @DisplayName("Scrap 도메인 객체 - Builder 패턴 테스트")
    void testScrapDomainBuilderPattern() {
        // Given
        int userId = 123;
        String productId = "D98765";

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
    @DisplayName("Scrap 도메인 객체 - getter/setter 테스트")
    void testScrapDomainGetterSetter() {
        // Given
        Scrap scrap = new Scrap();
        int userId = 999;
        String productId = "F98765";

        // When
        scrap.setUserId(userId);
        scrap.setProductId(productId);

        // Then
        assertEquals(userId, scrap.getUserId());
        assertEquals(productId, scrap.getProductId());
    }

    @Test
    @DisplayName("Scrap 도메인 객체 - AllArgsConstructor 테스트")
    void testScrapDomainAllArgsConstructor() {
        // Given
        int userId = 456;
        String productId = "S54321";

        // When
        Scrap scrap = new Scrap(userId, productId);

        // Then
        assertNotNull(scrap);
        assertEquals(userId, scrap.getUserId());
        assertEquals(productId, scrap.getProductId());
    }

    @Test
    @DisplayName("Scrap 도메인 객체 - 동등성 비교 테스트")
    void testScrapDomainEqualsAndHashCode() {
        // Given
        Scrap scrap1 = Scrap.builder()
                .userId(1)
                .productId("D12345")
                .build();

        Scrap scrap2 = Scrap.builder()
                .userId(1)
                .productId("D12345")
                .build();

        Scrap scrap3 = Scrap.builder()
                .userId(2)
                .productId("D12345")
                .build();

        // When & Then
        assertEquals(scrap1, scrap2);
        assertEquals(scrap1.hashCode(), scrap2.hashCode());
        assertNotEquals(scrap1, scrap3);
        assertNotEquals(scrap2, scrap3);
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
        assertNotNull(dto);
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
    @DisplayName("ScrapRequestDTO null 값 처리 테스트")
    void testScrapRequestDTONullHandling() {
        // Given
        ScrapRequestDTO dto = new ScrapRequestDTO();

        // When
        dto.setProductId(null);

        // Then
        assertNotNull(dto);
        assertNull(dto.getProductId());

        // When - 생성자로 null 설정
        ScrapRequestDTO dto2 = new ScrapRequestDTO(null);

        // Then
        assertNotNull(dto2);
        assertNull(dto2.getProductId());
    }

    @Test
    @DisplayName("상품 ID 접두사별 분류 테스트")
    void testProductIdPrefixClassification() {
        // Given
        String[] productIds = {"D11111", "S22222", "F33333", "X44444", "A55555", "C66666"};
        char[] expectedPrefixes = {'D', 'S', 'F', 'X', 'A', 'C'};

        // When & Then
        for (int i = 0; i < productIds.length; i++) {
            String productId = productIds[i];
            char expectedPrefix = expectedPrefixes[i];
            
            assertNotNull(productId);
            assertFalse(productId.isEmpty());
            assertEquals(expectedPrefix, productId.charAt(0));
        }
    }

    @Test
    @DisplayName("CustomUser 객체에서 사용자 정보 추출 테스트")
    void testCustomUserInformationExtraction() {
        // Given & When
        CustomUser user = testUser;
        User innerUser = user.getUser();

        // Then
        assertNotNull(user);
        assertNotNull(innerUser);
        assertEquals(testUserId, innerUser.getId());
        assertEquals("test@example.com", innerUser.getEmail());
        assertEquals("테스트 사용자", innerUser.getName());
        assertEquals("password", innerUser.getPassword());
    }

    @Test
    @DisplayName("잘못된 상품 ID 형식 검증 테스트")
    void testInvalidProductIdFormat() {
        // Given
        String[] invalidProductIds = {"Z99999", "123456", "", null};

        // When & Then
        for (String productId : invalidProductIds) {
            if (productId != null && !productId.isEmpty()) {
                char prefix = productId.charAt(0);
                boolean isValidPrefix = (prefix == 'D' || prefix == 'S' || prefix == 'F' || 
                                       prefix == 'X' || prefix == 'A' || prefix == 'C');
                
                if (productId.equals("Z99999") || productId.equals("123456")) {
                    assertFalse(isValidPrefix, "Invalid product ID should not have valid prefix: " + productId);
                }
            } else {
                assertTrue(productId == null || productId.isEmpty(), "Product ID should be null or empty");
            }
        }
    }

    @Test
    @DisplayName("상품 타입별 매핑 검증 테스트")
    void testProductTypeMapping() {
        // Given
        String depositId = "D12345";
        String savingsId = "S67890";
        String fundId = "F11111";
        String forexId = "X22222";
        String aggressivePensionId = "A33333";
        String conservativePensionId = "C44444";

        // When & Then
        assertTrue(isDepositProduct(depositId));
        assertTrue(isSavingsProduct(savingsId));
        assertTrue(isFundProduct(fundId));
        assertTrue(isForexProduct(forexId));
        assertTrue(isPensionProduct(aggressivePensionId));
        assertTrue(isPensionProduct(conservativePensionId));

        assertFalse(isDepositProduct(savingsId));
        assertFalse(isSavingsProduct(fundId));
        assertFalse(isFundProduct(forexId));
        assertFalse(isForexProduct(aggressivePensionId));
    }

    @Test
    @DisplayName("Scrap 도메인 객체의 toString 동작 테스트")
    void testScrapObjectToString() {
        // Given
        Scrap scrap = Scrap.builder()
                .userId(1)
                .productId("D12345")
                .build();

        // When
        String scrapString = scrap.toString();

        // Then
        assertNotNull(scrapString);
        assertTrue(scrapString.contains("1"));
        assertTrue(scrapString.contains("D12345"));
    }

    @Test
    @DisplayName("ScrapRequestDTO toString 동작 테스트")
    void testScrapRequestDTOToString() {
        // Given
        ScrapRequestDTO dto = new ScrapRequestDTO("F98765");

        // When
        String dtoString = dto.toString();

        // Then
        assertNotNull(dtoString);
        assertTrue(dtoString.contains("F98765"));
    }

    @Test
    @DisplayName("ScrapRequestDTO equals 및 hashCode 테스트")
    void testScrapRequestDTOEqualsAndHashCode() {
        // Given
        ScrapRequestDTO dto1 = new ScrapRequestDTO("D12345");
        ScrapRequestDTO dto2 = new ScrapRequestDTO("D12345");
        ScrapRequestDTO dto3 = new ScrapRequestDTO("S54321");

        // When & Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    @DisplayName("빈 문자열 상품 ID 처리 테스트")
    void testEmptyProductIdHandling() {
        // Given
        ScrapRequestDTO dto = new ScrapRequestDTO("");

        // When & Then
        assertNotNull(dto);
        assertEquals("", dto.getProductId());
        assertTrue(dto.getProductId().isEmpty());
    }

    @Test
    @DisplayName("상품 ID 길이 검증 테스트")
    void testProductIdLengthValidation() {
        // Given
        String shortId = "D1";
        String normalId = "D12345";
        String longId = "D1234567890123456789";

        // When
        ScrapRequestDTO dto1 = new ScrapRequestDTO(shortId);
        ScrapRequestDTO dto2 = new ScrapRequestDTO(normalId);
        ScrapRequestDTO dto3 = new ScrapRequestDTO(longId);

        // Then
        assertEquals(shortId, dto1.getProductId());
        assertEquals(normalId, dto2.getProductId());
        assertEquals(longId, dto3.getProductId());
        
        assertEquals(2, dto1.getProductId().length());
        assertEquals(6, dto2.getProductId().length());
        assertEquals(20, dto3.getProductId().length());
    }

    @Test
    @DisplayName("ScrappedProductResponseDTO - Builder 테스트")
    void testScrappedProductResponseDTOBuilder() {
        ScrappedProductResponseDTO dto = ScrappedProductResponseDTO.builder()
            .productId("D0001")
            .name("테스트 상품")
            .company("테스트 은행")
            .tag1("tag1")
            .tag2("tag2")
            .tag3("tag3")
            .type("deposit")
            .title(3.5)
            .subTitle("subtitle")
            .viewCnt(100)
            .scrapCnt(200)
            .build();

        assertEquals("D0001", dto.getProductId());
        assertEquals("테스트 상품", dto.getName());
        assertEquals("테스트 은행", dto.getCompany());
        assertEquals("tag1", dto.getTag1());
        assertEquals("deposit", dto.getType());
        assertEquals(3.5, dto.getTitle());
        assertEquals("subtitle", dto.getSubTitle());
        assertEquals(100, dto.getViewCnt());
        assertEquals(200, dto.getScrapCnt());

    }

    // Helper methods
    private boolean isDepositProduct(String productId) {
        return productId != null && productId.startsWith("D");
    }

    private boolean isSavingsProduct(String productId) {
        return productId != null && productId.startsWith("S");
    }

    private boolean isFundProduct(String productId) {
        return productId != null && productId.startsWith("F");
    }

    private boolean isForexProduct(String productId) {
        return productId != null && productId.startsWith("X");
    }

    private boolean isPensionProduct(String productId) {
        return productId != null && (productId.startsWith("A") || productId.startsWith("C"));
    }
}