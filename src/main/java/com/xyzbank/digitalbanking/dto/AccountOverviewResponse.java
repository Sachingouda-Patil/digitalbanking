package com.xyzbank.digitalbanking.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOverviewResponse {
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String currency;
}
