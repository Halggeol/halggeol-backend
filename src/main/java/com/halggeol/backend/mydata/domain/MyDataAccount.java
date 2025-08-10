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
    private String accountNum;
    @JsonProperty("is_consent")
    private Boolean isConsent;
    @JsonProperty("seqno")
    private Integer seqno;
    @JsonProperty("is_foreign_deposit")
    private Boolean isForeignDeposit;
    @JsonProperty("prod_name")
    private String prodName;
    @JsonProperty("is_minus")
    private Boolean isMinus;
    @JsonProperty("account_type")
    private String accountType;
    @JsonProperty("account_status")
    private String accountStatus;
    @JsonProperty("balance_amount")
    private String balanceAmount;
    @JsonProperty("currency_code")
    private String currencyCode;
}