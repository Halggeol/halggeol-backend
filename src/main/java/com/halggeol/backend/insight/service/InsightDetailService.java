package com.halggeol.backend.insight.service;

import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.RegretSurveyRequestDTO;
import com.halggeol.backend.domain.CustomUser;

public interface InsightDetailService {
    InsightDetailResponseDTO getInsightDetail(Integer round, String productId, CustomUser user);

    void updateRegretSurvey(CustomUser user, RegretSurveyRequestDTO dto);
}
