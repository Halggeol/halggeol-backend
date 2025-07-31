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

@DisplayName("DashboardService 인터페이스 테스트")
class DashboardServiceTest {

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
    @DisplayName("DashboardResponseDTO 완전한 필드 테스트")
    void testDashboardResponseDTOCompleteFields() {
        // Given
        DashboardResponseDTO dto = new DashboardResponseDTO();
        Double avgRegretScore = 7.5;
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        List<DashboardPortfolioResponseDTO> portfolio = new ArrayList<>();
        String userName = "테스트사용자";

        // 자산 데이터 생성
        DashboardAssetResponseDTO asset1 = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardAssetResponseDTO asset2 = new DashboardAssetResponseDTO("2024-02", "1200000");
        assets.add(asset1);
        assets.add(asset2);

        // 포트폴리오 데이터 생성
        DashboardPortfolioResponseDTO portfolio1 = new DashboardPortfolioResponseDTO("예금", 40.0);
        DashboardPortfolioResponseDTO portfolio2 = new DashboardPortfolioResponseDTO("적금", 35.0);
        DashboardPortfolioResponseDTO portfolio3 = new DashboardPortfolioResponseDTO("펀드", 25.0);
        portfolio.add(portfolio1);
        portfolio.add(portfolio2);
        portfolio.add(portfolio3);

        // When
        dto.setAvgRegretScore(avgRegretScore);
        dto.setAssets(assets);
        dto.setPortfolio(portfolio);
        dto.setUserName(userName);

        // Then
        assertEquals(avgRegretScore, dto.getAvgRegretScore());
        assertEquals(assets, dto.getAssets());
        assertEquals(portfolio, dto.getPortfolio());
        assertEquals(userName, dto.getUserName());
        assertEquals(2, dto.getAssets().size());
        assertEquals(3, dto.getPortfolio().size());
    }

    @Test
    @DisplayName("DashboardAssetResponseDTO 상세 테스트")
    void testDashboardAssetResponseDTODetailed() {
        // Given
        String date1 = "2024-01-01";
        String asset1 = "1000000";
        String date2 = "2024-02-01";
        String asset2 = "1500000";

        // When - Builder 패턴
        DashboardAssetResponseDTO dto1 = DashboardAssetResponseDTO.builder()
                .date(date1)
                .asset(asset1)
                .build();

        // When - AllArgsConstructor
        DashboardAssetResponseDTO dto2 = new DashboardAssetResponseDTO(date2, asset2);

        // When - NoArgsConstructor + Setter
        DashboardAssetResponseDTO dto3 = new DashboardAssetResponseDTO();
        dto3.setDate("2024-03-01");
        dto3.setAsset("2000000");

        // Then
        assertEquals(date1, dto1.getDate());
        assertEquals(asset1, dto1.getAsset());
        assertEquals(date2, dto2.getDate());
        assertEquals(asset2, dto2.getAsset());
        assertEquals("2024-03-01", dto3.getDate());
        assertEquals("2000000", dto3.getAsset());
    }

    @Test
    @DisplayName("DashboardPortfolioResponseDTO 상세 테스트")
    void testDashboardPortfolioResponseDTODetailed() {
        // Given
        String type1 = "예금";
        Double ratio1 = 45.5;
        String type2 = "적금";
        Double ratio2 = 30.0;

        // When - Builder 패턴
        DashboardPortfolioResponseDTO dto1 = DashboardPortfolioResponseDTO.builder()
                .type(type1)
                .ratio(ratio1)
                .build();

        // When - AllArgsConstructor
        DashboardPortfolioResponseDTO dto2 = new DashboardPortfolioResponseDTO(type2, ratio2);

        // When - NoArgsConstructor + Setter
        DashboardPortfolioResponseDTO dto3 = new DashboardPortfolioResponseDTO();
        dto3.setType("펀드");
        dto3.setRatio(24.5);

        // Then
        assertEquals(type1, dto1.getType());
        assertEquals(ratio1, dto1.getRatio());
        assertEquals(type2, dto2.getType());
        assertEquals(ratio2, dto2.getRatio());
        assertEquals("펀드", dto3.getType());
        assertEquals(24.5, dto3.getRatio());
    }

    @Test
    @DisplayName("포트폴리오 비율 합계 검증 테스트")
    void testPortfolioRatioSum() {
        // Given
        List<DashboardPortfolioResponseDTO> portfolio = new ArrayList<>();
        portfolio.add(new DashboardPortfolioResponseDTO("예금", 40.0));
        portfolio.add(new DashboardPortfolioResponseDTO("적금", 35.0));
        portfolio.add(new DashboardPortfolioResponseDTO("펀드", 25.0));

        // When
        double totalRatio = portfolio.stream()
                .mapToDouble(DashboardPortfolioResponseDTO::getRatio)
                .sum();

        // Then
        assertEquals(100.0, totalRatio, 0.01);
        assertEquals(3, portfolio.size());
    }

    @Test
    @DisplayName("자산 데이터 시계열 처리 테스트")
    void testAssetTimeSeriesData() {
        // Given
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        assets.add(new DashboardAssetResponseDTO("2024-01", "1000000"));
        assets.add(new DashboardAssetResponseDTO("2024-02", "1100000"));
        assets.add(new DashboardAssetResponseDTO("2024-03", "1200000"));
        assets.add(new DashboardAssetResponseDTO("2024-04", "1150000"));

        // When - 자산 증가율 계산 (단순 예시)
        boolean hasGrowth = false;
        for (int i = 1; i < assets.size(); i++) {
            long prev = Long.parseLong(assets.get(i-1).getAsset());
            long curr = Long.parseLong(assets.get(i).getAsset());
            if (curr > prev) {
                hasGrowth = true;
                break;
            }
        }

        // Then
        assertEquals(4, assets.size());
        assertTrue(hasGrowth); // 적어도 한 달은 증가했음
        assertEquals("2024-01", assets.get(0).getDate());
        assertEquals("2024-04", assets.get(3).getDate());
    }

    @Test
    @DisplayName("대시보드 데이터 null 안전성 테스트")
    void testDashboardDataNullSafety() {
        // Given
        DashboardResponseDTO dto = new DashboardResponseDTO();

        // When - null 값들 설정
        dto.setAvgRegretScore(null);
        dto.setAssets(null);
        dto.setPortfolio(null);
        dto.setUserName(null);

        // Then
        assertNull(dto.getAvgRegretScore());
        assertNull(dto.getAssets());
        assertNull(dto.getPortfolio());
        assertNull(dto.getUserName());

        // When - 빈 리스트 설정
        dto.setAssets(new ArrayList<>());
        dto.setPortfolio(new ArrayList<>());

        // Then
        assertNotNull(dto.getAssets());
        assertNotNull(dto.getPortfolio());
        assertTrue(dto.getAssets().isEmpty());
        assertTrue(dto.getPortfolio().isEmpty());
    }

    @Test
    @DisplayName("사용자 정보 추출 및 변환 테스트")
    void testUserInfoExtractionAndConversion() {
        // Given
        CustomUser user = testUser;

        // When
        int userId = user.getUser().getId();
        String userIdString = String.valueOf(userId);
        String userName = user.getUser().getName();
        String email = user.getUser().getEmail();

        // Then
        assertEquals(1, userId);
        assertEquals("1", userIdString);
        assertEquals(testUserId, userIdString);
        assertEquals("테스트 사용자", userName);
        assertEquals("test@example.com", email);
    }

    @Test
    @DisplayName("대시보드 데이터 통계 계산 테스트")
    void testDashboardDataStatistics() {
        // Given
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        assets.add(new DashboardAssetResponseDTO("2024-01", "1000000"));
        assets.add(new DashboardAssetResponseDTO("2024-02", "1200000"));
        assets.add(new DashboardAssetResponseDTO("2024-03", "1100000"));

        // When - 평균 자산 계산
        double avgAsset = assets.stream()
                .mapToLong(asset -> Long.parseLong(asset.getAsset()))
                .average()
                .orElse(0.0);

        // When - 최대/최소 자산 계산
        long maxAsset = assets.stream()
                .mapToLong(asset -> Long.parseLong(asset.getAsset()))
                .max()
                .orElse(0L);

        long minAsset = assets.stream()
                .mapToLong(asset -> Long.parseLong(asset.getAsset()))
                .min()
                .orElse(0L);

        // Then
        assertEquals(1100000.0, avgAsset, 0.01);
        assertEquals(1200000L, maxAsset);
        assertEquals(1000000L, minAsset);
    }

    @Test
    @DisplayName("DTO 동등성 및 해시코드 테스트")
    void testDTOEqualsAndHashCode() {
        // Given
        DashboardAssetResponseDTO asset1 = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardAssetResponseDTO asset2 = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardAssetResponseDTO asset3 = new DashboardAssetResponseDTO("2024-02", "2000000");

        DashboardPortfolioResponseDTO portfolio1 = new DashboardPortfolioResponseDTO("예금", 50.0);
        DashboardPortfolioResponseDTO portfolio2 = new DashboardPortfolioResponseDTO("예금", 50.0);
        DashboardPortfolioResponseDTO portfolio3 = new DashboardPortfolioResponseDTO("적금", 30.0);

        // When & Then - 자산 DTO
        assertEquals(asset1, asset2);
        assertEquals(asset1.hashCode(), asset2.hashCode());
        assertNotEquals(asset1, asset3);
        assertNotEquals(asset1.hashCode(), asset3.hashCode());

        // When & Then - 포트폴리오 DTO
        assertEquals(portfolio1, portfolio2);
        assertEquals(portfolio1.hashCode(), portfolio2.hashCode());
        assertNotEquals(portfolio1, portfolio3);
        assertNotEquals(portfolio1.hashCode(), portfolio3.hashCode());
    }

    @Test
    @DisplayName("대시보드 데이터 toString 메소드 테스트")
    void testDashboardDataToString() {
        // Given
        DashboardAssetResponseDTO asset = new DashboardAssetResponseDTO("2024-01", "1000000");
        DashboardPortfolioResponseDTO portfolio = new DashboardPortfolioResponseDTO("예금", 50.0);
        
        DashboardResponseDTO response = DashboardResponseDTO.builder()
                .avgRegretScore(7.5)
                .userName("테스트사용자")
                .build();

        // When
        String assetString = asset.toString();
        String portfolioString = portfolio.toString();
        String responseString = response.toString();

        // Then
        assertNotNull(assetString);
        assertNotNull(portfolioString);
        assertNotNull(responseString);
        
        assertTrue(assetString.contains("2024-01"));
        assertTrue(assetString.contains("1000000"));
        assertTrue(portfolioString.contains("예금"));
        assertTrue(portfolioString.contains("50.0"));
        assertTrue(responseString.contains("7.5"));
        assertTrue(responseString.contains("테스트사용자"));
    }

    @Test
    @DisplayName("비어있는 대시보드 데이터 처리 테스트")
    void testEmptyDashboardDataHandling() {
        // Given
        DashboardResponseDTO dto = new DashboardResponseDTO();
        List<DashboardAssetResponseDTO> emptyAssets = new ArrayList<>();
        List<DashboardPortfolioResponseDTO> emptyPortfolio = new ArrayList<>();

        // When
        dto.setAssets(emptyAssets);
        dto.setPortfolio(emptyPortfolio);
        dto.setAvgRegretScore(0.0);

        // Then
        assertNotNull(dto.getAssets());
        assertNotNull(dto.getPortfolio());
        assertTrue(dto.getAssets().isEmpty());
        assertTrue(dto.getPortfolio().isEmpty());
        assertEquals(0.0, dto.getAvgRegretScore());
    }

    @Test
    @DisplayName("대시보드 Builder 패턴 체이닝 테스트")
    void testDashboardBuilderChaining() {
        // Given & When
        DashboardResponseDTO dto = DashboardResponseDTO.builder()
                .avgRegretScore(8.5)
                .userName("체이닝테스트")
                .assets(new ArrayList<>())
                .portfolio(new ArrayList<>())
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(8.5, dto.getAvgRegretScore());
        assertEquals("체이닝테스트", dto.getUserName());
        assertNotNull(dto.getAssets());
        assertNotNull(dto.getPortfolio());
    }
}