package com.halggeol.backend.user.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCycleRequestDTO {
    public enum Cycle {
        WEEKLY_1,
        WEEKLY_2,
        MONTHLY_1
    }

    @NotBlank
    private String cycle;

    public void validateCycleType() {
        try {
            Cycle.valueOf(cycle);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "설정 가능한 주기가 아닙니다.");
        }
    }
}
