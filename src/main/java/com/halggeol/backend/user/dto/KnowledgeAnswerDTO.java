package com.halggeol.backend.user.dto;

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
public class KnowledgeAnswerDTO {
    @NotNull
    @Range(min = 0, max = 9)
    private Integer number;

    @NotNull
    @Range(min = 1, max = 3)
    private Integer score;

    @NotNull
    private Boolean correct;
}
