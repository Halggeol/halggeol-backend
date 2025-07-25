package com.halggeol.backend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// 메인 페이지에 들어갈 RecommendResponseDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendResponseDTO {
    private String productId;
    private String name;
    private Integer matchScore; // 퍼센트 값 (예: 50)
}
