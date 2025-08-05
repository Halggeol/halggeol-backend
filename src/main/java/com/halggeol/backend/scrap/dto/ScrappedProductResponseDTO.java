package com.halggeol.backend.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrappedProductResponseDTO {
    private String productId;
    private String name;
    private String company;
    private String tag1;
    private String tag2;
    private String tag3;

    private String type;

    private Double title;
    private String subTitle;

    private Integer viewCnt;
    private Integer scrapCnt;
}
