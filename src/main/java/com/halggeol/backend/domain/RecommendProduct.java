package com.halggeol.backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecommendProduct {
    private String productId;
    private String status;  // ENUM('가입', '관심', '회고')
    private String survey;
    private Integer regretScore;
    private Integer missAmount;
}
