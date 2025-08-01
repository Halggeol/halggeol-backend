package com.halggeol.backend.survey.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TendencySurveyRequestDTO {
    @Email
    private String email;

    @NotNull
    @Valid
    @Size(min = 8, max = 8)
    List<TendencySurveyItemDTO> answers;

    @NotNull
    @Valid
    @Size(min = 1, max = 3)
    List<TendencyExperienceItemDTO> experiences;

    public int getInvestmentPeriodOption() {
        return answers.get(6).getOption();
    }
}
