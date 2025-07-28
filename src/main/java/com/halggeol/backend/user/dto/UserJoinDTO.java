package com.halggeol.backend.user.dto;

import com.halggeol.backend.security.domain.User;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
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

    // 최소 2자 이상의 한글로 구성
    @NotBlank // null값은 정규식에서 판단하지 않아서 필요함
    @Pattern(regexp = "^[가-힣]{2,}$")
    private String name;

    // 최소 8자 이상의 영문 대/소문자, 숫자, 특수문자로 구성
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,}$")
    private String password;

    @NotBlank
    private String confirmPassword;

    // 01012345678
    @NotBlank
    @Pattern(regexp = "^01[016789]\\d{3,4}\\d{4}$")
    private String phone;

    // 만 14세 이상
    @NotBlank
    private String birth;
//    @Past
//    private LocalDateTime birth;

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
            .build();
    }
}
