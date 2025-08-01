package com.halggeol.backend.user.dto;

import com.halggeol.backend.security.util.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileDTO {
    @NotBlank
    @Pattern(regexp = RegexConstants.PHONE_PATTERN)
    private String phone;
}
