package com.halggeol.backend.recommend.service.calculater.strategy;

import com.halggeol.backend.recommend.dto.FundAlgorithmResponseDTO;
import com.halggeol.backend.recommend.service.calculater.model.Score;
import com.halggeol.backend.recommend.service.calculater.util.ScoreUtil;

public class FundScoreStrategy implements ScoreStrategy<FundAlgorithmResponseDTO>{


    @Override
    public Score calculate(FundAlgorithmResponseDTO dto) {
        double yieldScore = ScoreUtil.normalizeRate(dto.getRate());
        double movementScore = ScoreUtil.normalizeFundPriceMovement(dto.getFundPriceMovement());
        double riskScore = 1 - ScoreUtil.normalizeRate(dto.getRate()) * 0.8 + movementScore * 0.2;
        double costScore = 1 - ScoreUtil.normalizeFundTER(dto.getTER());
        double liquidityScore = 1.0;

        double complexityScore;
        if (dto.getCategory().contains("주식형")) complexityScore = 1.0;
        else if (dto.getCategory().contains("채권형")) complexityScore = 0.6;
        else if (dto.getCategory().contains("혼합형")) complexityScore = 0.4;
        else complexityScore = 0.2; // 기타 카테고리

        return new Score(dto.getId(),yieldScore, riskScore, costScore, liquidityScore, complexityScore);
    }
}
