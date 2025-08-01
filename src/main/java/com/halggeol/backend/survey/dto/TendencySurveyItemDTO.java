package com.halggeol.backend.survey.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TendencySurveyItemDTO {
    @NotNull
    @Range(min = 1, max = 10)
    private Integer number; // 문항 번호

    @NotNull
    @Range(min = 1, max = 5)
    private Integer option; // 선택한 번호

    @NotNull
    @Range(min = 0, max = 6)
    private Integer score; // 배점
}
