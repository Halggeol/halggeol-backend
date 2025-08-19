package com.halggeol.backend.survey.service;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.survey.dto.TendencyExperienceItemDTO;
import com.halggeol.backend.survey.dto.TendencySurveyItemDTO;
import com.halggeol.backend.survey.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import java.util.List;
import java.util.Map;

public interface SurveyService {

    Map<String, String> initKnowledge(KnowledgeSurveyRequestDTO surveyResult);

    Map<String, String> updateKnowledge(CustomUser user, KnowledgeSurveyRequestDTO surveyResult);

    Map<String, String> initTendency(TendencySurveyRequestDTO surveyResult);

    Map<String, String> updateTendency(CustomUser user, TendencySurveyRequestDTO surveyResult);

    int calculateRisk(TendencySurveyRequestDTO surveyRequest);

    int calculateTotalScore(List<TendencySurveyItemDTO> surveyResult);

    int calculateMaxExperienceScore(List<TendencyExperienceItemDTO> answers);

    int classifyInvestmentType(int risk, int investmentPeriodOption);
}
