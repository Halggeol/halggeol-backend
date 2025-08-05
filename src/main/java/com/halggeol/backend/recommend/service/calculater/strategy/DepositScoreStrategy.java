package com.halggeol.backend.recommend.service.calculater.strategy;

import com.halggeol.backend.recommend.dto.DepositAlgorithmResponseDTO;
import com.halggeol.backend.recommend.service.calculater.ScoreCalculator;
import com.halggeol.backend.recommend.service.calculater.model.Score;
import com.halggeol.backend.recommend.service.calculater.util.ScoreUtil;

public class DepositScoreStrategy implements ScoreStrategy<DepositAlgorithmResponseDTO>{

    @Override
    public Score calculate(DepositAlgorithmResponseDTO dto) {
        double yieldScore = ScoreUtil.normalizeRate(dto.getRate());
        double riskScore = ScoreUtil.fixedRisk(6);
        double costScore = 0.0;

        double liquidityScore = ((dto.getMinSaveTerm() - ScoreCalculator.MAX_SAVE_TERM)
                / (ScoreCalculator.MIN_SAVE_TERM - ScoreCalculator.MAX_SAVE_TERM) * 0.9
                + (dto.getExtraDeposit() ? 0.1 : 0.0));

        double complexityScore = ScoreUtil.joinDenyScore(dto.getJoinDeny());

        return new Score(yieldScore, riskScore, costScore, liquidityScore, complexityScore);
    }
}
