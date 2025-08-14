package com.halggeol.backend.survey.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.survey.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import com.halggeol.backend.survey.service.SurveyService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("SurveyController Mockito 단위 테스트")
class SurveyControllerTest {

    @Mock
    private SurveyService surveyService;
    @InjectMocks
    private SurveyController surveyController;

    private CustomUser testUser;
    private int testUserId;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        User user = User.builder()
            .id(1)
            .email("test@example.com")
            .name("테스트 사용자")
            .password("password")
            .build();
        testUser = new CustomUser(user);
    }

    @DisplayName("initKnowledge: 성공 응답 확인")
    @Test
    void initKnowledge_success() {
        /* Given */
        KnowledgeSurveyRequestDTO knowledgeSurveyRequestDTO = new KnowledgeSurveyRequestDTO();

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "금융 이해도 초기화 성공");

        given(surveyService.initKnowledge(any(KnowledgeSurveyRequestDTO.class)))
            .willReturn(Map.of("message", "금융 이해도 초기화 성공"));

        /* When */
        ResponseEntity<?> response = surveyController.initKnowledge(knowledgeSurveyRequestDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(surveyService, times(1)).initKnowledge(eq(knowledgeSurveyRequestDTO));
    }

    @DisplayName("updateKnowledge: 성공 응답 확인")
    @Test
    void updateKnowledge_success() {
        /* Given */
        KnowledgeSurveyRequestDTO knowledgeSurveyRequestDTO = new KnowledgeSurveyRequestDTO();

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "금융 이해도 업데이트 성공");

        given(surveyService.updateKnowledge(any(CustomUser.class), any(KnowledgeSurveyRequestDTO.class)))
            .willReturn(Map.of("message", "금융 이해도 업데이트 성공"));

        /* When */
        ResponseEntity<?> response = surveyController.updateKnowledge(testUser, knowledgeSurveyRequestDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(surveyService, times(1)).updateKnowledge(eq(testUser), eq(knowledgeSurveyRequestDTO));
    }

    @DisplayName("initTendency: 성공 응답 확인")
    @Test
    void initTendency_success() {
        /* Given */
        TendencySurveyRequestDTO tendencySurveyRequestDTO = new TendencySurveyRequestDTO();

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "투자 성향 초기화 성공");

        given(surveyService.initTendency(any(TendencySurveyRequestDTO.class)))
            .willReturn(Map.of("message", "투자 성향 초기화 성공"));

        /* When */
        ResponseEntity<?> response = surveyController.initTendency(tendencySurveyRequestDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(surveyService, times(1)).initTendency(eq(tendencySurveyRequestDTO));
    }

    @DisplayName("updateTendency: 성공 응답 확인")
    @Test
    void updateTendency_success() {
        /* Given */
        TendencySurveyRequestDTO tendencySurveyRequestDTO = new TendencySurveyRequestDTO();

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "투자 성향 업데이트 성공");

        given(surveyService.updateTendency(any(CustomUser.class), any(TendencySurveyRequestDTO.class)))
            .willReturn(Map.of("message", "투자 성향 업데이트 성공"));

        /* When */
        ResponseEntity<?> response = surveyController.updateTendency(testUser, tendencySurveyRequestDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(surveyService, times(1)).updateTendency(eq(testUser), eq(tendencySurveyRequestDTO));
    }
}