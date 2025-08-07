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
import org.hibernate.validator.constraints.Range;
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
    @Size(min = 7, max = 7)
    private List<TendencySurveyItemDTO> answers;

    @NotNull
    @Valid
    @Size(min = 1, max = 3)
    private List<TendencyExperienceItemDTO> experiences;

    @NotNull
    @Valid
    @Range(min = 1, max = 5)
    private Integer investmentPeriod;

    public int getInvestmentPeriodOption() {
        return investmentPeriod;
    }
}
