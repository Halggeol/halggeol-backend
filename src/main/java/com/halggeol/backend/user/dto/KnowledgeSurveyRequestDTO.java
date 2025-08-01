package com.halggeol.backend.user.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeSurveyRequestDTO {
    @NotEmpty
    private List<KnowledgeAnswerDTO> answers;
}
