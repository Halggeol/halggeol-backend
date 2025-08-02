package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("FundTrackVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundTrack {
    private Integer id;

    private String productId;

    private LocalDateTime trackingDate; // 추종일자

    private Integer price;

    private Double profit; // 수익률

}
