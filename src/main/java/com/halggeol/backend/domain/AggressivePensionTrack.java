package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("AggressivePensionTrackVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggressivePensionTrack {
    private Integer id;

    private String productId;

    private LocalDateTime trackingDate;

    private Integer price;

    private Double profit;
}
