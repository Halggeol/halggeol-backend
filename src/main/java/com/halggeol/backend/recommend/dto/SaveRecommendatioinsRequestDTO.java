package com.halggeol.backend.recommend.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRecommendatioinsRequestDTO {
    private Integer userId;
    private String product1Id;
    private String product2Id;
    private String product3Id;
    private String product4Id;
    private String product5Id;
}
