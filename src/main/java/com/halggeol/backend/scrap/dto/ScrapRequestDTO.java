package com.halggeol.backend.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ScrapRequestDTO {
    private String productId;
}
