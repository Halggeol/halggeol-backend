package com.halggeol.backend.insight.dto;

import lombok.Data;

@Data
public class RegretSurveyRequestDTO {
    private String productId; // 상품 id
    private Boolean isRegretted; // 피드백 설문 후회 여부
    private Integer regrettedReason; // 피드백 설문 후회 이유
}
