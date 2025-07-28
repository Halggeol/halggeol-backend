package com.halggeol.backend.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReverifyPasswordDTO {
    // 최소 8자 이상의 영문 대/소문자, 숫자, 특수문자로 구성
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,}$")
    private String confirmPassword;
}
