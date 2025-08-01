package com.halggeol.backend.security.dto;

import com.halggeol.backend.security.util.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReverifyPasswordDTO {
    @NotBlank
    @Pattern(regexp = RegexConstants.PASSWORD_PATTERN)
    private String confirmPassword;
}
