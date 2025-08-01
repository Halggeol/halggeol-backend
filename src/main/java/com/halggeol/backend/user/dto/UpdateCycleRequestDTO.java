package com.halggeol.backend.user.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCycleRequestDTO {
    @NotBlank
    private String cycle;

    public boolean isValidCycle() {
        return cycle != null
            && (cycle.equals("WEEKLY_1")
            || cycle.equals("WEEKLY_2")
            || cycle.equals("MONTHLY_1"));
    }
}
