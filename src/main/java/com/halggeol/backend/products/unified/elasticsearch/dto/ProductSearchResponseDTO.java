package com.halggeol.backend.products.unified.elasticsearch.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductSearchResponseDTO {
    private String productId;
    private String name;
    private String company;
    private String tag1;
    private String tag2;
    private String tag3;
    private String type;
    private Double title;
    private String subTitle;
    private Integer fSector;
    private Integer saveTerm;
    private Integer minSaveTerm;
    private Integer maxSaveTerm;
    private Integer minAmount;
    private Integer viewCnt;
    private Integer scrapCnt;

}
