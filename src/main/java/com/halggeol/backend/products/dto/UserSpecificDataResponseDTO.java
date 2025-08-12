package com.halggeol.backend.products.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSpecificDataResponseDTO {
    private String advantage;
    private String disadvantage;
    private boolean isScraped;
}
