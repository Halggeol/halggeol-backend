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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        return answers.stream()
            .filter(answer -> answer.getNumber() == 8)
            .findFirst()
            .map(TendencySurveyItemDTO::getOption)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "8번 문항이 누락되었습니다."));
    }
}
