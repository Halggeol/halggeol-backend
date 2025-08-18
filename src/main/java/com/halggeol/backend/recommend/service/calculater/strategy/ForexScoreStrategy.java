package com.halggeol.backend.recommend.service.calculater.strategy;

import com.halggeol.backend.recommend.dto.ForexAlgorithmResponseDTO;
import com.halggeol.backend.recommend.service.calculater.ScoreCalculator;
import com.halggeol.backend.recommend.service.calculater.model.Score;
import com.halggeol.backend.recommend.service.calculater.util.ScoreUtil;

public class ForexScoreStrategy implements ScoreStrategy<ForexAlgorithmResponseDTO> {

    @Override
    public Score calculate(ForexAlgorithmResponseDTO dto) {
        if (dto == null) return null;

        double yieldScore = ScoreUtil.normalizeRate(dto.getRate());
        double riskScore = ScoreUtil.fixedRisk(dto.getRisk());
        double costScore = 1.5 / ScoreCalculator.MAX_FUND_TER;

        double liquidityScore = ((dto.getMinSaveTerm() - ScoreCalculator.MIN_SAVE_TERM)
                / (ScoreCalculator.MAX_SAVE_TERM - ScoreCalculator.MIN_SAVE_TERM)) * 0.9
                + ("Y".equals(dto.getAutoRenew()) ? 0.1 : 0.9);

        double complexityScore = ScoreUtil.joinDenyScore(dto.getTaxRefund())
                + (dto.getProtect() == 1 ? 0.1 : 0.0);

        return new Score(dto.getId(),yieldScore, riskScore, costScore, liquidityScore, complexityScore);
    }


}
