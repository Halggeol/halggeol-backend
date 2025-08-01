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
public class KnowledgeSurveyRequestDTO {
    @Email
    private String email;

    @NotNull
    @Valid
    @Size(min = 10, max = 10)
    private List<KnowledgeSurveyItemDTO> answers;
}
