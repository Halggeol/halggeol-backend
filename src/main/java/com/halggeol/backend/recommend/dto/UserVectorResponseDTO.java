package com.halggeol.backend.recommend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVectorResponseDTO {

    private String id;
    private Double yieldScore;
    private Double riskScore;
    private Double costScore;
    private Double liquidityScore;
    private Double complexityScore;

}
