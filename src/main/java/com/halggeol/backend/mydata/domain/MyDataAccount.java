package com.halggeol.backend.mydata.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyDataAccount {
    @JsonProperty("account_num")
    private String account_num;
    @JsonProperty("is_consent")
    private Boolean is_consent;
    @JsonProperty("seqno")
    private Integer seqno;
    @JsonProperty("is_foreign_deposit")
    private Boolean is_foreign_deposit;
    @JsonProperty("prod_name")
    private String prod_name;
    @JsonProperty("is_minus")
    private Boolean is_minus;
    @JsonProperty("account_type")
    private String account_type;
    @JsonProperty("account_status")
    private String account_status;
    @JsonProperty("balance_amount")
    private String balance_amount;
    @JsonProperty("currency_code")
    private String currency_code;
}