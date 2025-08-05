package com.halggeol.backend.recommend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductVectorUpdateResponseDTO {

    private String productId;
    private Double yieldScore;
    private Double riskScore;
    private Double costScore;
    private Double liquidityScore;
    private Double complexityScore;
    private Double algoCode;
}
