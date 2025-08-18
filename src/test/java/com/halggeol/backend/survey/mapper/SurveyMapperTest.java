package com.halggeol.backend.survey.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("SurveyMapper Mockito 단위 테스트")
class SurveyMapperTest {

    @Mock
    private SurveyMapper surveyMapper;

    @DisplayName("updateKnowledgeByEmail 호출 여부 확인")
    @Test
    void updateKnowledgeByEmail_shouldBeCalled() {
        /* Given */
        String email = "test@example.com";
        int userKlg = 5;

        given(surveyMapper.updateKnowledgeByEmail(email, userKlg)).willReturn(1);

        /* When */
        int rows = surveyMapper.updateKnowledgeByEmail(email, userKlg);

        /* Then */
        assertThat(rows).isEqualTo(1);
        verify(surveyMapper, times(1)).updateKnowledgeByEmail(email, userKlg);
    }

    @DisplayName("updateTendencyByEmail 호출 여부 확인")
    @Test
    void updateTendencyByEmail_shouldBeCalled() {
        /* Given */
        String email = "test@example.com";
        int risk = 3;
        double yieldScore = 1.1;
        double riskScore = 2.2;
        double costScore = 3.3;
        double liquidityScore = 4.4;
        double complexityScore = 5.5;

        given(surveyMapper.updateTendencyByEmail(email, risk, yieldScore, riskScore, costScore, liquidityScore, complexityScore))
            .willReturn(1);

        /* When */
        int rows = surveyMapper.updateTendencyByEmail(
            email, risk, yieldScore, riskScore, costScore, liquidityScore, complexityScore
        );

        /* Then */
        assertThat(rows).isEqualTo(1);
        verify(surveyMapper, times(1)).updateTendencyByEmail(
            email, risk, yieldScore, riskScore, costScore, liquidityScore, complexityScore
        );
    }
}
