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
public class FindEmailDTO {
    @NotBlank
    @Pattern(regexp = RegexConstants.NAME_PATTERN)
    private String name;

    @NotBlank
    @Pattern(regexp = RegexConstants.PHONE_PATTERN)
    private String phone;
}
