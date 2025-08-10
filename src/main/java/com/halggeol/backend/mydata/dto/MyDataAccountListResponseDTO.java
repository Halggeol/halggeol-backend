package com.halggeol.backend.mydata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.halggeol.backend.mydata.domain.MyDataAccount;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyDataAccountListResponseDTO {
    @JsonProperty("rsp_code")
    private String rsp_code;
    @JsonProperty("rsp_msg")
    private String rsp_msg;
    @JsonProperty("search_timestamp")
    private String search_timestamp;
    @JsonProperty("reg_date")
    private String reg_date;
    @JsonProperty("next_page")
    private String next_page;
    @JsonProperty("account_cnt")
    private Integer account_cnt;
    @JsonProperty("account_list")
    private List<MyDataAccount> account_list;
}
