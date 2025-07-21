package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("MyProductVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProduct {
    private int id;
    private int mydataId;
    private Integer amount;
    private LocalDateTime regDate;
    private LocalDateTime endDate;
    private String productId;
}
