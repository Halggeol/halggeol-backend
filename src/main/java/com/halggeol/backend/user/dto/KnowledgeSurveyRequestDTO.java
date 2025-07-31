package com.halggeol.backend.user.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
    @NotNull
    @Valid
    @Size(min = 10, max = 10)
    private List<KnowledgeAnswerDTO> answers;
}
