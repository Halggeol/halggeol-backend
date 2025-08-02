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
    private int id; // 마이데이터 id 자동생성 1부터 시작
    private LocalDateTime collectDate; // 마이데이터 수집일자 매일 있음
    private Integer asset; // 자산(원화) 수집일자의 자산
    private int userId; // 유저 id

    private List<MyProduct> myProducts;
}
