package com.halggeol.backend.survey.service;

import com.halggeol.backend.recommend.dto.UserVectorResponseDTO;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.survey.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.survey.dto.TendencyExperienceItemDTO;
import com.halggeol.backend.survey.dto.TendencySurveyItemDTO;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import com.halggeol.backend.survey.mapper.SurveyMapper;
import com.halggeol.backend.user.service.UserService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyMapper surveyMapper;
    private final UserService userService;
    private final RecommendService recommendService;

    @Override
    public Map<String, String> initKnowledge(KnowledgeSurveyRequestDTO surveyResult) {
        String email = surveyResult.getEmail();
        userService.emailExists(email);

        int userKlg = 0; // TODO: surveyResult로 점수 내기

        surveyMapper.updateKnowledgeByEmail(email, userKlg);
        return Map.of("message", "금융 이해도 설정이 완료되었습니다.");
    }

    @Override
    public Map<String, String> updateKnowledge(
        CustomUser user,
        KnowledgeSurveyRequestDTO surveyResult
    ) {
        String email = user.getUser().getEmail();

        int userKlg = 0; // TODO: surveyResult로 점수 내기

        surveyMapper.updateKnowledgeByEmail(email, userKlg);
        return Map.of("message", "금융 이해도 갱신이 완료되었습니다.");
    }

    @Override
    public Map<String, String> initTendency(TendencySurveyRequestDTO surveyResult) {
        String email = surveyResult.getEmail();
        userService.emailExists(email);

        int risk = classifyInvestmentType(
            calculateRisk(surveyResult),
            surveyResult.getInvestmentPeriodOption()
        );

        UserVectorResponseDTO scores = recommendService.initUserVector(surveyResult); // TODO: 5개 user score 점수 내기

        surveyMapper.updateTendencyByEmail(
            email,
            risk,
            scores.getYieldScore(),
            scores.getRiskScore(),
            scores.getCostScore(),
            scores.getLiquidityScore(),
            scores.getComplexityScore()
        );
        return Map.of("message", "투자 성향 설정이 완료되었습니다.");
    }

    @Override
    public Map<String, String> updateTendency(
        CustomUser user,
        TendencySurveyRequestDTO surveyResult
    ) {
        String email = user.getUser().getEmail();

        int risk = classifyInvestmentType(
            calculateRisk(surveyResult),
            surveyResult.getInvestmentPeriodOption()
        );

//        UserVectorResponseDTO scores = recommendService.; // TODO: 5개 user score 점수 내기
        UserVectorResponseDTO scores = recommendService.initUserVector(surveyResult); // TODO: 5개 user score 점수 내기

        surveyMapper.updateTendencyByEmail(
            email,
            risk,
            scores.getYieldScore(),
            scores.getRiskScore(),
            scores.getCostScore(),
            scores.getLiquidityScore(),
            scores.getComplexityScore()
        );
        return Map.of("message", "투자 성향 갱신이 완료되었습니다.");
    }

    @Override
    public int calculateRisk(TendencySurveyRequestDTO surveyRequest) {
        return calculateTotalScore(surveyRequest.getAnswers())
            + calculateMaxExperienceScore(surveyRequest.getExperiences());
    }

    @Override
    public int calculateTotalScore(List<TendencySurveyItemDTO> answers) {
        // 1~7번 문항 점수 합산
        return answers.stream()
            .mapToInt(TendencySurveyItemDTO::getOptionScore)
            .sum();
    }

    @Override
    public int calculateMaxExperienceScore(List<TendencyExperienceItemDTO> answers) {
        // 8번 문항(투자경험)은 중복 선택 시 최고 점수만 반영
        if (answers == null || answers.isEmpty() || answers.get(0).getOption() == 1) {
            return 0;
        }

        return answers.stream()
            .mapToInt(exp -> exp.getOptionScore() + exp.getPeriodScore())
            .max()
            .orElse(0);
    }

    @Override
    public int classifyInvestmentType(int risk, int investmentPeriodOption) {
        // 투자자 유형 분류
        final int INVESTMENT_TYPE_AGGRESSIVE = 1;
        final int INVESTMENT_TYPE_ACTIVE = 2;
        final int INVESTMENT_TYPE_RISK_NEUTRAL = 3;
        final int INVESTMENT_TYPE_SAFETY_SEEKING = 4;
        final int INVESTMENT_TYPE_STABLE = 5;

        // 9번 문항(투자 예정 기간) 기준 재분류
        if (risk >= 30) {
            if (investmentPeriodOption == 1) return INVESTMENT_TYPE_RISK_NEUTRAL;
            return INVESTMENT_TYPE_AGGRESSIVE;
        }
        else if (risk >= 25) {
            if (investmentPeriodOption >= 4) return INVESTMENT_TYPE_AGGRESSIVE;
            else if (investmentPeriodOption >= 2) return INVESTMENT_TYPE_ACTIVE;
            else return INVESTMENT_TYPE_RISK_NEUTRAL;
        }
        else if (risk >= 20) {
            if (investmentPeriodOption == 5) return INVESTMENT_TYPE_AGGRESSIVE;
            else if (investmentPeriodOption >= 3) return INVESTMENT_TYPE_ACTIVE;
            else if (investmentPeriodOption == 2) return INVESTMENT_TYPE_RISK_NEUTRAL;
            else return INVESTMENT_TYPE_SAFETY_SEEKING;
        }
        else if (risk >= 15) {
            if (investmentPeriodOption >= 3) return INVESTMENT_TYPE_RISK_NEUTRAL;
            else return INVESTMENT_TYPE_SAFETY_SEEKING;
        }
        else {
            if (investmentPeriodOption >= 4) return INVESTMENT_TYPE_RISK_NEUTRAL;
            if (investmentPeriodOption >= 2) return INVESTMENT_TYPE_SAFETY_SEEKING;
            return INVESTMENT_TYPE_STABLE;
        }
    }
}
