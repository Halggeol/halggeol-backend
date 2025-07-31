package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardPortfolioResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DashboardServiceImpl 테스트")
class DashboardServiceImplTest {

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
    @DisplayName("DashboardResponseDTO 빌더 패턴 테스트")
    void testDashboardResponseDTOBuilder() {
        // Given
        Double avgRegretScore = 7.5;
        String userName = "테스트 사용자";
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        List<DashboardPortfolioResponseDTO> portfolio = new ArrayList<>();

        // When
        DashboardResponseDTO dto = DashboardResponseDTO.builder()
                .avgRegretScore(avgRegretScore)
                .userName(userName)
                .assets(assets)
                .portfolio(portfolio)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(avgRegretScore, dto.getAvgRegretScore());
        assertEquals(userName, dto.getUserName());
        assertEquals(assets, dto.getAssets());
        assertEquals(portfolio, dto.getPortfolio());
    }

    @Test
    @DisplayName("DashboardResponseDTO getter/setter 테스트")
    void testDashboardResponseDTOGetterSetter() {
        // Given
        DashboardResponseDTO dto = new DashboardResponseDTO();
        Double avgScore = 8.0;
        String userName = "테스트";
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        List<DashboardPortfolioResponseDTO> portfolio = new ArrayList<>();

        // When
        dto.setAvgRegretScore(avgScore);
        dto.setUserName(userName);
        dto.setAssets(assets);
        dto.setPortfolio(portfolio);

        // Then
        assertEquals(avgScore, dto.getAvgRegretScore());
        assertEquals(userName, dto.getUserName());
        assertEquals(assets, dto.getAssets());
        assertEquals(portfolio, dto.getPortfolio());
    }

    @Test
    @DisplayName("DashboardAssetResponseDTO 빌더 패턴 테스트")
    void testDashboardAssetResponseDTOBuilder() {
        // Given
        String date = "2024-01-15";
        String asset = "1000000";

        // When
        DashboardAssetResponseDTO dto = DashboardAssetResponseDTO.builder()
                .date(date)
                .asset(asset)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(date, dto.getDate());
        assertEquals(asset, dto.getAsset());
    }

    @Test
    @DisplayName("DashboardPortfolioResponseDTO 빌더 패턴 테스트")
    void testDashboardPortfolioResponseDTOBuilder() {
        // Given
        String type = "예금";
        Double ratio = 50.0;

        // When
        DashboardPortfolioResponseDTO dto = DashboardPortfolioResponseDTO.builder()
                .type(type)
                .ratio(ratio)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(type, dto.getType());
        assertEquals(ratio, dto.getRatio());
    }

    @Test
    @DisplayName("CustomUser 객체 생성 테스트")
    void testCustomUserCreation() {
        // Given & When
        CustomUser user = testUser;

        // Then
        assertNotNull(user);
        assertNotNull(user.getUser());
        assertEquals(1, user.getUser().getId());
        assertEquals("test@example.com", user.getUser().getEmail());
        assertEquals("테스트 사용자", user.getUser().getName());
    }

    @Test
    @DisplayName("null 값 처리 테스트")
    void testNullValueHandling() {
        // Given & When
        DashboardResponseDTO dto = DashboardResponseDTO.builder()
                .avgRegretScore(null)
                .userName(null)
                .assets(null)
                .portfolio(null)
                .build();

        // Then
        assertNotNull(dto);
        assertNull(dto.getAvgRegretScore());
        assertNull(dto.getUserName());
        assertNull(dto.getAssets());
        assertNull(dto.getPortfolio());
    }

    @Test
    @DisplayName("빈 리스트 처리 테스트")
    void testEmptyListHandling() {
        // Given
        List<DashboardAssetResponseDTO> emptyAssets = new ArrayList<>();
        List<DashboardPortfolioResponseDTO> emptyPortfolio = new ArrayList<>();

        // When
        DashboardResponseDTO dto = DashboardResponseDTO.builder()
                .assets(emptyAssets)
                .portfolio(emptyPortfolio)
                .build();

        // Then
        assertNotNull(dto);
        assertNotNull(dto.getAssets());
        assertNotNull(dto.getPortfolio());
        assertTrue(dto.getAssets().isEmpty());
        assertTrue(dto.getPortfolio().isEmpty());
    }

    @Test
    @DisplayName("User ID 문자열 변환 테스트")
    void testUserIdStringConversion() {
        // Given
        int userId = testUser.getUser().getId();

        // When
        String userIdString = String.valueOf(userId);

        // Then
        assertEquals("1", userIdString);
        assertEquals(testUserId, userIdString);
    }

    @Test
    @DisplayName("자산 데이터 리스트 생성 테스트")
    void testAssetDataListCreation() {
        // Given
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        
        DashboardAssetResponseDTO asset1 = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardAssetResponseDTO asset2 = new DashboardAssetResponseDTO("2024-02", "1200000");

        // When
        assets.add(asset1);
        assets.add(asset2);

        // Then
        assertNotNull(assets);
        assertEquals(2, assets.size());
        assertEquals("2024-01", assets.get(0).getDate());
        assertEquals("1000000", assets.get(0).getAsset());
        assertEquals("2024-02", assets.get(1).getDate());
        assertEquals("1200000", assets.get(1).getAsset());
    }

    @Test
    @DisplayName("포트폴리오 데이터 리스트 생성 테스트")
    void testPortfolioDataListCreation() {
        // Given
        List<DashboardPortfolioResponseDTO> portfolio = new ArrayList<>();
        
        DashboardPortfolioResponseDTO item1 = new DashboardPortfolioResponseDTO("예금", 40.0);
        DashboardPortfolioResponseDTO item2 = new DashboardPortfolioResponseDTO("적금", 35.0);
        DashboardPortfolioResponseDTO item3 = new DashboardPortfolioResponseDTO("펀드", 25.0);

        // When
        portfolio.add(item1);
        portfolio.add(item2);
        portfolio.add(item3);

        // Then
        assertNotNull(portfolio);
        assertEquals(3, portfolio.size());
        
        double totalRatio = portfolio.stream()
                .mapToDouble(DashboardPortfolioResponseDTO::getRatio)
                .sum();
        assertEquals(100.0, totalRatio);
    }

    @Test
    @DisplayName("DashboardResponseDTO AllArgsConstructor 테스트")
    void testDashboardResponseDTOAllArgsConstructor() {
        // Given
        Double avgRegretScore = 6.5;
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        List<DashboardPortfolioResponseDTO> portfolio = new ArrayList<>();
        String userName = "테스트사용자";

        // When
        DashboardResponseDTO dto = new DashboardResponseDTO(
            avgRegretScore, assets, portfolio, null, userName, null);

        // Then
        assertNotNull(dto);
        assertEquals(avgRegretScore, dto.getAvgRegretScore());
        assertEquals(assets, dto.getAssets());
        assertEquals(portfolio, dto.getPortfolio());
        assertEquals(userName, dto.getUserName());
    }

    @Test
    @DisplayName("DTO toString 메소드 테스트")
    void testDTOToStringMethod() {
        // Given
        DashboardAssetResponseDTO assetDto = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardPortfolioResponseDTO portfolioDto = new DashboardPortfolioResponseDTO("예금", 50.0);

        // When
        String assetString = assetDto.toString();
        String portfolioString = portfolioDto.toString();

        // Then
        assertNotNull(assetString);
        assertNotNull(portfolioString);
        assertTrue(assetString.contains("2024-01"));
        assertTrue(assetString.contains("1000000"));
        assertTrue(portfolioString.contains("예금"));
        assertTrue(portfolioString.contains("50.0"));
    }

    @Test
    @DisplayName("DTO equals 및 hashCode 테스트")
    void testDTOEqualsAndHashCode() {
        // Given
        DashboardAssetResponseDTO asset1 = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardAssetResponseDTO asset2 = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardAssetResponseDTO asset3 = new DashboardAssetResponseDTO("2024-02", "2000000");

        DashboardPortfolioResponseDTO portfolio1 = new DashboardPortfolioResponseDTO("예금", 50.0);
        DashboardPortfolioResponseDTO portfolio2 = new DashboardPortfolioResponseDTO("예금", 50.0);
        DashboardPortfolioResponseDTO portfolio3 = new DashboardPortfolioResponseDTO("적금", 30.0);

        // When & Then
        assertEquals(asset1, asset2);
        assertEquals(asset1.hashCode(), asset2.hashCode());
        assertNotEquals(asset1, asset3);

        assertEquals(portfolio1, portfolio2);
        assertEquals(portfolio1.hashCode(), portfolio2.hashCode());
        assertNotEquals(portfolio1, portfolio3);
    }

    @Test
    @DisplayName("데이터 타입 검증 테스트")
    void testDataTypeValidation() {
        // Given
        DashboardResponseDTO dto = new DashboardResponseDTO();

        // When
        dto.setAvgRegretScore(7.5);

        // Then
        assertTrue(dto.getAvgRegretScore() instanceof Double);
        assertEquals(7.5, dto.getAvgRegretScore(), 0.01);
    }

    @Test
    @DisplayName("빈 생성자 테스트")
    void testNoArgsConstructors() {
        // Given & When
        DashboardResponseDTO responseDto = new DashboardResponseDTO();
        DashboardAssetResponseDTO assetDto = new DashboardAssetResponseDTO();
        DashboardPortfolioResponseDTO portfolioDto = new DashboardPortfolioResponseDTO();

        // Then
        assertNotNull(responseDto);
        assertNotNull(assetDto);
        assertNotNull(portfolioDto);
        
        assertNull(responseDto.getAvgRegretScore());
        assertNull(assetDto.getDate());
        assertNull(portfolioDto.getType());
    }
}