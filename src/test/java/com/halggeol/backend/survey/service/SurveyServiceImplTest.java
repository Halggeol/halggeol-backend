package com.halggeol.backend.survey.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.survey.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.survey.dto.TendencyExperienceItemDTO;
import com.halggeol.backend.survey.dto.TendencySurveyItemDTO;
import com.halggeol.backend.survey.mapper.SurveyMapper;
import com.halggeol.backend.user.service.UserService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("SurveyService Mockito 단위 테스트")
class SurveyServiceImplTest {

    @Mock
    private SurveyMapper surveyMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private SurveyServiceImpl surveyService;

    @Test
    @DisplayName("initKnowledge 정상 동작")
    void initKnowledgeTest() {
        /* Given */
        KnowledgeSurveyRequestDTO dto = new KnowledgeSurveyRequestDTO();
        dto.setEmail("test@example.com");

        doNothing().when(userService).emailExists(anyString());

        /* When */
        Map<String, String> result = surveyService.initKnowledge(dto);

        /* Then */
        verify(userService).emailExists("test@example.com");
        verify(surveyMapper).updateKnowledgeByEmail(eq("test@example.com"), anyInt());
        assertThat(result).containsEntry("message", "금융 이해도 설정이 완료되었습니다.");
    }

    @Test
    @DisplayName("updateKnowledge 정상 동작")
    void updateKnowledgeTest() {
        /* Given */
        User user = User.builder()
            .id(1)
            .email("test@example.com")
            .name("테스트 사용자")
            .password("password")
            .build();
        CustomUser customUser = new CustomUser(user);

        KnowledgeSurveyRequestDTO dto = new KnowledgeSurveyRequestDTO();

        /* When */
        Map<String, String> result = surveyService.updateKnowledge(customUser, dto);

        /* Then */
        verify(surveyMapper).updateKnowledgeByEmail(eq("test@example.com"), anyInt());
        assertThat(result).containsEntry("message", "금융 이해도 갱신이 완료되었습니다.");
    }

    @Test
    @DisplayName("calculateTotalScore 점수 합산")
    void calculateTotalScoreTest() {
        List<TendencySurveyItemDTO> answers = List.of(
            createTendencySurveyItem(3),
            createTendencySurveyItem(5),
            createTendencySurveyItem(7)
        );

        int sum = surveyService.calculateTotalScore(answers);

        assertThat(sum).isEqualTo(15);
    }

    @Test
    @DisplayName("calculateMaxExperienceScore 최고 점수 반환")
    void calculateMaxExperienceScoreTest() {
        /* Given */
        List<TendencyExperienceItemDTO> experiences = List.of(
            createTendencyExperienceItem(3, 2),
            createTendencyExperienceItem(5, 1),
            createTendencyExperienceItem(7, 0)
        );

        /* When */
        int maxScore = surveyService.calculateMaxExperienceScore(experiences);

        /* Then */
        assertThat(maxScore).isEqualTo(7); // 7 + 0 = 7 이 최대
    }

    @Test
    @DisplayName("calculateMaxExperienceScore 빈 리스트 또는 기본값 시 0 반환")
    void calculateMaxExperienceScoreEmptyTest() {
        assertThat(surveyService.calculateMaxExperienceScore(null)).isEqualTo(0);
        assertThat(surveyService.calculateMaxExperienceScore(List.of(createTendencyExperienceItem(0, 0)))).isEqualTo(0);
    }

    @Test
    @DisplayName("classifyInvestmentType 테스트")
    void classifyInvestmentTypeTest() {
        // risk 32, period 1 -> RISK_NEUTRAL (3)
        assertThat(surveyService.classifyInvestmentType(32, 1)).isEqualTo(3);
        // risk 32, period 2 -> AGGRESSIVE (1)
        assertThat(surveyService.classifyInvestmentType(32, 2)).isEqualTo(1);
        // risk 26, period 4 -> AGGRESSIVE (1)
        assertThat(surveyService.classifyInvestmentType(26, 4)).isEqualTo(1);
        // risk 26, period 1 -> RISK_NEUTRAL (3)
        assertThat(surveyService.classifyInvestmentType(26, 1)).isEqualTo(3);
        // risk 14, period 2 -> SAFETY_SEEKING (4)
        assertThat(surveyService.classifyInvestmentType(14, 2)).isEqualTo(4);
    }

    // 헬퍼 메서드
    private TendencySurveyItemDTO createTendencySurveyItem(int score) {
        TendencySurveyItemDTO dto = new TendencySurveyItemDTO();
        dto.setOptionScore(score);
        return dto;
    }

    private TendencyExperienceItemDTO createTendencyExperienceItem(int optionScore, int periodScore) {
        TendencyExperienceItemDTO dto = new TendencyExperienceItemDTO();
        dto.setOptionScore(optionScore);
        dto.setPeriodScore(periodScore);
        dto.setOption(optionScore == 0 ? 1 : 2);  // option==1 시 0 반환 테스트용
        return dto;
    }
}
