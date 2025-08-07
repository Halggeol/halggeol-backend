package com.halggeol.backend.dashboard.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardAssetResponseDTO implements Serializable {
    private String date;
    private String asset;
}
