package com.halggeol.backend.logs.processor;

import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.domain.UserActionLog;
import com.halggeol.backend.recommend.dto.ProductVectorResponseDTO;
import com.halggeol.backend.recommend.mapper.RecommendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class UserActionLogProcessor implements ItemProcessor<UserActionLog, User> {
    private final RecommendMapper recommendMapper;

    @Override
    @Bean
    public User process(UserActionLog userActionLog) {
        // UserActionLog에서 필요한 정보를 추출하여 User 객체를 생성
        User user = recommendMapper.getUserById(userActionLog.getUserId());
        ProductVectorResponseDTO productVector = recommendMapper.getProductVectorById(userActionLog.getProductId());
        double actionScore = switch (userActionLog.getActionType()) {
            case "view" -> 1;
            case "scroll" -> 5;
            case "scrap" -> 10;
            case "regret" -> 20;
            case "purchase" -> 50;
            case "resign" -> -20;
            default ->
                // 다른 액션 타입에 대한 처리
                0;
        };
        user.setComplexityScore(user.getComplexityScore() + ((user.getComplexityScore()- productVector.getComplexityScore()) * actionScore/100));
        user.setLiquidityScore(user.getLiquidityScore() + ((user.getLiquidityScore() - productVector.getLiquidityScore()) * actionScore/100));
        user.setCostScore(user.getCostScore() + ((user.getCostScore() - productVector.getCostScore()) * actionScore/100));
        user.setYieldScore(user.getYieldScore() + ((user.getYieldScore() - productVector.getYieldScore()) * actionScore/100));
        user.setRiskScore(user.getRiskScore() + ((user.getRiskScore() - productVector.getRiskScore()) * actionScore/100));
        // UserActionLog의 액션 타입에 따라 User 객체를 업데이트
        return user;
    }
}
