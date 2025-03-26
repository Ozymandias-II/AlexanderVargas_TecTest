package com.bank_system.account_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDTO {

    private Long accountId;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private Boolean status;
    private Long clientId;
}
