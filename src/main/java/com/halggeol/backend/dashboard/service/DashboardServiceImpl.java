package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
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
//    private final UserService userService; // 원래 UserService를 호출하고 싶었으나, 에러나서 Mapper 코드로 대체
    private final RecommendService recommendService;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponseDTO getDashboardData(String userId) {
        DashboardResponseDTO dashboardResponse = new DashboardResponseDTO();

        // 1. 'recommendItems' 데이터 채우기 (기존 RecommendService 활용)
        List<RecommendResponseDTO> recommendedProducts = recommendService.getRecommendProducts(userId);
        dashboardResponse.setRecommendItems(recommendedProducts);
//
//        // avgRegretScore
////        dashboardResponse.setAvgRegretScore(55);
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
////        // regretRanking
////        List<RegretRankingDTO> regretRanking = new ArrayList<>();
////        regretRanking.add(new RegretRankingDTO(1, "A12", "상품명1", 1, 4.5, 12));
////        regretRanking.add(new RegretRankingDTO(2, "B34", "상품명2", 1, null, null)); // rate, period는 1위만 있다고 가정
////        regretRanking.add(new RegretRankingDTO(3, "C56", "상품명3", 2, null, null));
////        regretRanking.add(new RegretRankingDTO(4, "D78", "상품명4", 2, null, null));
////        regretRanking.add(new RegretRankingDTO(5, "E90", "상품명5", 1, null, null));
////        dashboardResponse.setRegretRanking(regretRanking);
//
//        // userName
        String userName = userMapper.findNameById(Integer.parseInt(userId));
        dashboardResponse.setUserName(userName);
//
        return dashboardResponse;
    }
}
