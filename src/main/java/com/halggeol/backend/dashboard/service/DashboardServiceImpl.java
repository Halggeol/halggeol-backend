package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.dashboard.mapper.DashboardMapper;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.service.UnifiedProductService;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.user.mapper.UserMapper;
import com.halggeol.backend.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserMapper userMapper;
    private final DashboardMapper dashboardMapper;
//    private final UserService userService; // 원래 UserService를 호출하고 싶었으나, 에러나서 Mapper 코드로 대체
    private final RecommendService recommendService;
    private final UnifiedProductService unifiedProductService;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponseDTO getDashboardData(String userId) {
        DashboardResponseDTO dashboardResponse = new DashboardResponseDTO();

        // 1. 'recommendItems' 데이터 채우기 (기존 RecommendService 활용)
        List<RecommendResponseDTO> recommendedProducts = recommendService.getRecommendProducts(userId);
        dashboardResponse.setRecommendItems(recommendedProducts);

        Double avgRegretScore = dashboardMapper.getAvgRegretScoreByUserId(userId);
        dashboardResponse.setAvgRegretScore(avgRegretScore);
//
////        // assets (예시: 1년치 더미 데이터 생성)
////        List<AssetDTO> assets = new ArrayList<>();
////        LocalDate today = LocalDate.now(); // 현재 날짜
////        LocalDate oneYearAgo = today.minusYears(1); // 1년 전 날짜
////        for (int i = 0; i <= 365; i++) { // 366일 데이터 (1년 전 날짜 포함 당일까지)
////            LocalDate currentDate = oneYearAgo.plusDays(i);
////            assets.add(new AssetDTO(currentDate.toString(), String.valueOf(1000000 + i * 33))); // 임의의 자산 값
////        }
////        dashboardResponse.setAssets(assets);
////
////        // portfolio
////        List<PortfolioDTO> portfolio = new ArrayList<>();
////        portfolio.add(new PortfolioDTO("savings", "0.4"));
////        portfolio.add(new PortfolioDTO("fund", "0.3"));
////        dashboardResponse.setPortfolio(portfolio);
////
        List<UnifiedProductRegretRankingResponseDTO> regretRanking = unifiedProductService.getRegretRankingProducts();
        dashboardResponse.setRegretRanking(regretRanking);

        String userName = userMapper.findNameById(Integer.parseInt(userId));
        dashboardResponse.setUserName(userName);
//
        return dashboardResponse;
    }
}
