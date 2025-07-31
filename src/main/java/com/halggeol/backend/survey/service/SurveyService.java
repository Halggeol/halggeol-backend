package com.halggeol.backend.survey.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.user.dto.EditProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.survey.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import java.util.Map;

public interface SurveyService {
    Map<String, String> updateKnowledge(KnowledgeSurveyRequestDTO surveyResult);

    Map<String, String> updateTendency(TendencySurveyRequestDTO surveyResult);
}
