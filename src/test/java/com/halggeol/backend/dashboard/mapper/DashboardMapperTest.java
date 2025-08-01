package com.halggeol.backend.dashboard.mapper;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardPortfolioResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DashboardMapper 테스트")
class DashboardMapperTest {

    @Test
    @DisplayName("DashboardAssetResponseDTO 생성 및 getter/setter 테스트")
    void testDashboardAssetResponseDTOCreation() {
        // Given
        String date = "2024-01-15";
        String asset = "1000000";

        // When - Builder 패턴 테스트
        DashboardAssetResponseDTO dto = DashboardAssetResponseDTO.builder()
                .date(date)
                .asset(asset)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(date, dto.getDate());
        assertEquals(asset, dto.getAsset());

        // When - Setter 테스트
        DashboardAssetResponseDTO dto2 = new DashboardAssetResponseDTO();
        dto2.setDate("2024-02-15");
        dto2.setAsset("2000000");

        // Then
        assertEquals("2024-02-15", dto2.getDate());
        assertEquals("2000000", dto2.getAsset());
    }

    @Test
    @DisplayName("DashboardPortfolioResponseDTO 생성 및 getter/setter 테스트")
    void testDashboardPortfolioResponseDTOCreation() {
        // Given
        String type = "예금";
        Double ratio = 50.0;

        // When - Builder 패턴 테스트
        DashboardPortfolioResponseDTO dto = DashboardPortfolioResponseDTO.builder()
                .type(type)
                .ratio(ratio)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(type, dto.getType());
        assertEquals(ratio, dto.getRatio());

        // When - Setter 테스트
        DashboardPortfolioResponseDTO dto2 = new DashboardPortfolioResponseDTO();
        dto2.setType("적금");
        dto2.setRatio(30.0);

        // Then
        assertEquals("적금", dto2.getType());
        assertEquals(30.0, dto2.getRatio());
    }

    @Test
    @DisplayName("DashboardAssetResponseDTO AllArgsConstructor 테스트")
    void testDashboardAssetResponseDTOAllArgsConstructor() {
        // Given
        String date = "2024-01-01";
        String asset = "5000000";

        // When
        DashboardAssetResponseDTO dto = new DashboardAssetResponseDTO(date, asset);

        // Then
        assertNotNull(dto);
        assertEquals(date, dto.getDate());
        assertEquals(asset, dto.getAsset());
    }

    @Test
    @DisplayName("DashboardPortfolioResponseDTO AllArgsConstructor 테스트")
    void testDashboardPortfolioResponseDTOAllArgsConstructor() {
        // Given
        String type = "펀드";
        Double ratio = 25.0;

        // When
        DashboardPortfolioResponseDTO dto = new DashboardPortfolioResponseDTO(type, ratio);

        // Then
        assertNotNull(dto);
        assertEquals(type, dto.getType());
        assertEquals(ratio, dto.getRatio());
    }

    @Test
    @DisplayName("자산 데이터 리스트 처리 테스트")
    void testAssetDataListHandling() {
        // Given
        List<DashboardAssetResponseDTO> assets = new ArrayList<>();
        
        DashboardAssetResponseDTO asset1 = DashboardAssetResponseDTO.builder()
                .date("2024-01")
                .asset("1000000")
                .build();

        DashboardAssetResponseDTO asset2 = DashboardAssetResponseDTO.builder()
                .date("2024-02")
                .asset("1100000")
                .build();

        // When
        assets.add(asset1);
        assets.add(asset2);

        // Then
        assertNotNull(assets);
        assertEquals(2, assets.size());
        assertEquals("2024-01", assets.get(0).getDate());
        assertEquals("2024-02", assets.get(1).getDate());
        assertEquals("1000000", assets.get(0).getAsset());
        assertEquals("1100000", assets.get(1).getAsset());
    }

    @Test
    @DisplayName("포트폴리오 데이터 리스트 처리 테스트")
    void testPortfolioDataListHandling() {
        // Given
        List<DashboardPortfolioResponseDTO> portfolio = new ArrayList<>();
        
        DashboardPortfolioResponseDTO item1 = DashboardPortfolioResponseDTO.builder()
                .type("예금")
                .ratio(50.0)
                .build();

        DashboardPortfolioResponseDTO item2 = DashboardPortfolioResponseDTO.builder()
                .type("적금")
                .ratio(30.0)
                .build();

        DashboardPortfolioResponseDTO item3 = DashboardPortfolioResponseDTO.builder()
                .type("펀드")
                .ratio(20.0)
                .build();

        // When
        portfolio.add(item1);
        portfolio.add(item2);
        portfolio.add(item3);

        // Then
        assertNotNull(portfolio);
        assertEquals(3, portfolio.size());
        assertEquals("예금", portfolio.get(0).getType());
        assertEquals("적금", portfolio.get(1).getType());
        assertEquals("펀드", portfolio.get(2).getType());
        
        Double totalRatio = portfolio.stream()
                .mapToDouble(DashboardPortfolioResponseDTO::getRatio)
                .sum();
        assertEquals(100.0, totalRatio);
    }

    @Test
    @DisplayName("빈 리스트 처리 테스트")
    void testEmptyListHandling() {
        // Given
        List<DashboardAssetResponseDTO> emptyAssets = new ArrayList<>();
        List<DashboardPortfolioResponseDTO> emptyPortfolio = new ArrayList<>();

        // When & Then
        assertNotNull(emptyAssets);
        assertNotNull(emptyPortfolio);
        assertTrue(emptyAssets.isEmpty());
        assertTrue(emptyPortfolio.isEmpty());
        assertEquals(0, emptyAssets.size());
        assertEquals(0, emptyPortfolio.size());
    }

    @Test
    @DisplayName("자산 데이터 null 값 처리 테스트")
    void testAssetDataNullHandling() {
        // Given & When
        DashboardAssetResponseDTO dto = DashboardAssetResponseDTO.builder()
                .date(null)
                .asset(null)
                .build();

        // Then
        assertNotNull(dto);
        assertNull(dto.getDate());
        assertNull(dto.getAsset());

        // When - setter로 null 설정
        DashboardAssetResponseDTO dto2 = new DashboardAssetResponseDTO();
        dto2.setDate(null);
        dto2.setAsset(null);

        // Then
        assertNull(dto2.getDate());
        assertNull(dto2.getAsset());
    }

    @Test
    @DisplayName("포트폴리오 데이터 null 값 처리 테스트")
    void testPortfolioDataNullHandling() {
        // Given & When
        DashboardPortfolioResponseDTO dto = DashboardPortfolioResponseDTO.builder()
                .type(null)
                .ratio(null)
                .build();

        // Then
        assertNotNull(dto);
        assertNull(dto.getType());
        assertNull(dto.getRatio());

        // When - setter로 null 설정
        DashboardPortfolioResponseDTO dto2 = new DashboardPortfolioResponseDTO();
        dto2.setType(null);
        dto2.setRatio(null);

        // Then
        assertNull(dto2.getType());
        assertNull(dto2.getRatio());
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
}