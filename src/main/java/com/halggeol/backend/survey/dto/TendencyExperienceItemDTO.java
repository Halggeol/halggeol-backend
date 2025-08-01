package com.halggeol.backend.survey.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TendencyExperienceItemDTO {
    @NotNull
    @Range(min = 1, max = 4)
    private Integer option; // 투자 경험

    @NotNull
    @Range(min = 0, max = 6)
    private Integer optionScore; // 배점

    @Range(min = 1, max = 3)
    private Integer period; // 투자 경험 당 기간

    @NotNull
    @Range(min = 1, max = 5)
    private Integer periodScore; // 기간 배점
}
