package com.halggeol.backend.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO {
    // test@example.com
    @NotBlank
    @Email // RFC 5322의 간략한 버전으로 검사
    private String email;
}
