package com.halggeol.backend.products.dto;


import lombok.Data;

@Data
public class RegretRankingResponseDTO {

    private String productId;
    private String name;
    private String company;
    private Integer regretCnt;
    private float rate;
}
