package com.halggeol.backend.dashboard.service;

import com.halggeol.backend.dashboard.dto.DashboardAssetResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardPortfolioResponseDTO;
import com.halggeol.backend.dashboard.dto.DashboardResponseDTO;
import com.halggeol.backend.dashboard.mapper.DashboardMapper;
//import com.halggeol.backend.products.service.ProductDetailService;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.service.UnifiedProductService;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.user.mapper.UserMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserMapper userMapper;
    private final DashboardMapper dashboardMapper;

    // TODO: 원래 UserService를 호출하고 싶었으나, 에러나서 Mapper 코드로 대체, 추후 순환참조 문제 해결하고 수정할 것
//    private final UserService userService; 
    private final RecommendService recommendService;
    private final UnifiedProductService unifiedProductService;
//    private final ProductDetailService productDetailService;

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "userDashboardDataCache", key = "#user?.getUser()?.getId() != null ? #user.getUser().getId() : 'anonymous'")
    public DashboardResponseDTO getDashboardData(CustomUser user) {
        DashboardResponseDTO dashboardResponse = new DashboardResponseDTO();

        if (user == null) {
            List<UnifiedProductRegretRankingResponseDTO> regretRanking = unifiedProductService.getRegretRankingProducts();
            dashboardResponse.setRegretRanking(regretRanking);

            return dashboardResponse;
        }

        String userId = String.valueOf(user.getUser().getId());

        List<RecommendResponseDTO> recommendedProducts = recommendService.getRecommendProducts(userId);
        dashboardResponse.setRecommendItems(recommendedProducts);

        Double avgRegretScore = dashboardMapper.getAvgRegretScoreByUserId(userId);
        dashboardResponse.setAvgRegretScore(avgRegretScore);

        List<DashboardAssetResponseDTO> assets = dashboardMapper.getAssetsOneYearByUserId(userId);
        dashboardResponse.setAssets(assets);

        List<DashboardPortfolioResponseDTO> portfolio = dashboardMapper.getPortfolioByUserId(userId);
        dashboardResponse.setPortfolio(portfolio);

        List<UnifiedProductRegretRankingResponseDTO> regretRanking = unifiedProductService.getRegretRankingProducts();
        dashboardResponse.setRegretRanking(regretRanking);

        String userName = userMapper.findNameById(Integer.parseInt(userId));
        dashboardResponse.setUserName(userName);

        Double feedbackRatio = dashboardMapper.getFeedbackRatioByUserId(userId);
        dashboardResponse.setFeedbackRatio(feedbackRatio);

//        productDetailService.cacheUserRecommendedProducts(userId, recommendedProducts);

        return dashboardResponse;
    }

    @Override
    @Transactional
    public List<DashboardAssetResponseDTO> getAssetsOneYearByUserId(String userId) {
        return dashboardMapper.getAssetsOneYearByUserId(userId);
    }
}
