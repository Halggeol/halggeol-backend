package com.halggeol.backend.recommend.service.calculater.strategy;

import com.halggeol.backend.recommend.dto.SavingsAlgorithmResponseDTO;
import com.halggeol.backend.recommend.service.calculater.ScoreCalculator;
import com.halggeol.backend.recommend.service.calculater.model.Score;
import com.halggeol.backend.recommend.service.calculater.util.ScoreUtil;

public class SavingsScoreStrategy implements ScoreStrategy<SavingsAlgorithmResponseDTO>{


    @Override
    public Score calculate(SavingsAlgorithmResponseDTO dto) {
        double yieldScore = ScoreUtil.normalizeRate(dto.getRate());
        double riskScore = ScoreUtil.fixedRisk(6);
        double costScore = 0.0; // No costs associated with savings accounts

        double liquidityScore = ((dto.getMinSaveTerm() - ScoreCalculator.MAX_SAVE_TERM))
                / (ScoreCalculator.MIN_SAVE_TERM - ScoreCalculator.MAX_SAVE_TERM) * 0.9
                + (dto.getRateType().contains("단리") ? 0.1 : 0.0);

        double complexityScore = ScoreUtil.joinDenyScore(dto.getJoinDeny())
                + (dto.getRateType().contains("단리") ? 0.1 : 0.0);

        return new Score(yieldScore, riskScore, costScore, liquidityScore, complexityScore);
    }
}
