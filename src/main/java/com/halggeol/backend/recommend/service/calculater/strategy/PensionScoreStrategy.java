package com.halggeol.backend.recommend.service.calculater.strategy;

import com.halggeol.backend.recommend.dto.PensionAlgorithmResponseDTO;
import com.halggeol.backend.recommend.service.calculater.ScoreCalculator;
import com.halggeol.backend.recommend.service.calculater.model.Score;
import com.halggeol.backend.recommend.service.calculater.util.ScoreUtil;

public class PensionScoreStrategy implements ScoreStrategy<PensionAlgorithmResponseDTO> {

    @Override
    public Score calculate(PensionAlgorithmResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        boolean isAggressive = dto.getId().startsWith("A");

        double yieldScore = ScoreUtil.normalizeRate(dto.getRate());
        double riskScore = isAggressive ? ScoreUtil.fixedRisk(dto.getRisk()) : ScoreUtil.fixedRisk(6);
        double costScore = isAggressive ? ScoreUtil.normalizeFundTER(dto.getTER()) : 0.0; // No costs for conservative products
        double liquidityScore = isAggressive ? 1.0 : 1.0 - dto.getSaveTerm() / ScoreCalculator.MAX_SAVE_TERM;

        double complexityScore;
        if (isAggressive) {
            if (dto.getPensionKind().contains("주식")) {
                complexityScore = 1.0;
            } else if (dto.getPensionKind().contains("혼합")) {
                complexityScore = 0.6;
            } else if (dto.getPensionKind().contains("펀드")) {
                complexityScore = 0.4;
            } else  if (dto.getPensionKind().contains("채권")) {
                complexityScore = 0.2; // 기타 카테고리
            } else {
                complexityScore = 0.1; // 기타 연금은 가장 단순한 것으로 간주
            }
        } else {
            complexityScore = 0.5 + ScoreUtil.joinDenyScore(dto.getFSector());
        }

        return new Score(yieldScore, riskScore, costScore, liquidityScore, complexityScore);
    }
}
