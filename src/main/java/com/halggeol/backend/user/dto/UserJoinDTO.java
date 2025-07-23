package com.halggeol.backend.user.dto;

import com.halggeol.backend.security.account.domain.User;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinDTO {
    // test@example.com
    @NotBlank
    @Email // RFC 5322의 간략한 버전으로 검사
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
    private String checkPassword;

    // 01012345678
    @NotBlank
    @Pattern(regexp = "^01[016789]\\d{3,4}\\d{4}$")
    private String phone;

    // 만 14세 이상
    @NotBlank
    @Past
    private LocalDateTime birth;

    public User toVO() {
        return User.builder()
            .email(email)
            .name(name)
            .password(password)
            .phone(phone)
            .birth(birth)
            .build();
    }
}
