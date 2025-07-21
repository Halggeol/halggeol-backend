package com.halggeol.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForexTrack {

    private Integer id;

    private String productId;

    private String trackingDate; // DATE → String 또는 LocalDate

    private Integer price;
}
