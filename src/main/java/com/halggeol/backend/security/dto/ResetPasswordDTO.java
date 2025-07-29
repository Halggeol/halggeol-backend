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
public class ResetPasswordDTO {
    @NotBlank
    @Pattern(regexp = RegexConstants.PASSWORD_PATTERN)
    private String newPassword;

    @NotBlank
    @Pattern(regexp = RegexConstants.PASSWORD_PATTERN)
    private String confirmPassword;

    public boolean isPasswordConfirmed() {
        return confirmPassword.equals(newPassword);
    }
}
