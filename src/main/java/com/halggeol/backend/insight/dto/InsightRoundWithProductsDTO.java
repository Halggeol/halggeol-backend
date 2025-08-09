package com.halggeol.backend.insight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsightRoundWithProductsDTO {
    private int round;
    private List<InsightDTO> products;  // 상품 상세 리스트
}

