package com.halggeol.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindEmailDTO {
    private String name;
    private String phone;
}
