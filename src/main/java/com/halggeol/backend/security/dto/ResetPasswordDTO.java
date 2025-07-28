package com.halggeol.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {
    private String newPassword;
    private String confirmPassword;

    public boolean isPasswordConfirmed() {
        return confirmPassword.equals(newPassword);
    }
}
