package com.halggeol.backend.products.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductFeedbackRequestDTO {
    String pid;
    Integer uid;
    String reason;
    Date anlzDate;
}
