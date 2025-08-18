package com.halggeol.backend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVectorResponseDTO {

    private String id;
    private Double yieldScore;
    private Double riskScore;
    private Double costScore;
    private Double liquidityScore;
    private Double complexityScore;

}
