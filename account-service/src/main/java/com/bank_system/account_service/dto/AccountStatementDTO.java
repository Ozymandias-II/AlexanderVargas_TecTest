package com.bank_system.account_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountStatementDTO {
    private AccountResponseDTO account;
    private List<TransactionResponseDTO> transactions;
    private BigDecimal initialBalance;
    private BigDecimal finalBalance;
}
