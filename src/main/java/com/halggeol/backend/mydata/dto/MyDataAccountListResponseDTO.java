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
    private String rspCode;
    @JsonProperty("rsp_msg")
    private String rspMsg;
    @JsonProperty("search_timestamp")
    private String searchTimestamp;
    @JsonProperty("reg_date")
    private String regDate;
    @JsonProperty("next_page")
    private String nextPage;
    @JsonProperty("account_cnt")
    private Integer accountCnt;
    @JsonProperty("account_list")
    private List<MyDataAccount> accountList;
}
