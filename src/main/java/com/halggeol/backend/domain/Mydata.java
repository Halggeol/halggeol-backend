package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("MydataVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mydata {
    private int id;
    private LocalDateTime collectDate;
    private Integer asset;
    private int userId;

    private List<MyProduct> myProducts;
}
