package com.halggeol.backend.user.dto;

import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.security.util.RegexConstants;
import java.time.LocalDateTime;
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
public class UserJoinDTO {
    private String email;

    @NotBlank // null값은 정규식에서 판단하지 않아서 필요함
    @Pattern(regexp = RegexConstants.NAME_PATTERN)
    private String name;

    @NotBlank
    @Pattern(regexp = RegexConstants.PASSWORD_PATTERN)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Pattern(regexp = RegexConstants.PHONE_PATTERN)
    private String phone;

    // 만 14세 이상
    @NotBlank
    private String birth;

    public LocalDateTime getBirth() {
        return LocalDateTime.parse(birth + "T00:00:00");
    }

    public boolean isValidAge() {
        LocalDateTime convertedBirth = getBirth();
        return convertedBirth.plusYears(14).isBefore(LocalDateTime.now())
            || convertedBirth.plusYears(14).isEqual(LocalDateTime.now());
    }

    public boolean isCorrectPassword() {
        return password.equals(confirmPassword);
    }

    public User toVO() {
        return User.builder()
            .email(email)
            .name(name)
            .password(password)
            .phone(phone)
            .birth(LocalDateTime.parse(birth + "T00:00:00"))
            .insightCycle("MONTHLY_1")
            .build();
    }
}
