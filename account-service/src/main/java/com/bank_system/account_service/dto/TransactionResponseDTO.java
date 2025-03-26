package com.bank_system.account_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private Long transactionId;
    private LocalDateTime date;
    private String transactionType;
    private BigDecimal value;
    private BigDecimal balance;
    private Long accountId;
}
