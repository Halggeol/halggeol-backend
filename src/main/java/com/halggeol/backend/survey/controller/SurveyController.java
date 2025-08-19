package com.halggeol.backend.survey.controller;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.survey.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import com.halggeol.backend.survey.service.SurveyService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/survey")
public class SurveyController {
    private final SurveyService surveyService;

    @PatchMapping("/knowledge/init")
    public ResponseEntity<Map<String, String>> initKnowledge(
        @Valid @RequestBody KnowledgeSurveyRequestDTO surveyResult
    ) {
        // 최초 금융 이해도 설문
        return ResponseEntity.ok(surveyService.initKnowledge(surveyResult));
    }

    @PatchMapping("/knowledge")
    public ResponseEntity<Map<String, String>> updateKnowledge(
        @AuthenticationPrincipal CustomUser user,
        @Valid @RequestBody KnowledgeSurveyRequestDTO surveyResult
    ) {
        // 금융 이해도 갱신
        return ResponseEntity.ok(surveyService.updateKnowledge(user, surveyResult));
    }

    @PatchMapping("/tendency/init")
    public ResponseEntity<Map<String, String>> initTendency(
        @Valid @RequestBody TendencySurveyRequestDTO surveyResult
    ) {
        // 최초 투자 성향 설문
        return ResponseEntity.ok(surveyService.initTendency(surveyResult));
    }

    @PatchMapping("/tendency")
    public ResponseEntity<Map<String, String>> updateTendency(
        @AuthenticationPrincipal CustomUser user,
        @Valid @RequestBody TendencySurveyRequestDTO surveyResult
    ) {
        // 투자 성향 갱신
        return ResponseEntity.ok(surveyService.updateTendency(user, surveyResult));
    }
}
