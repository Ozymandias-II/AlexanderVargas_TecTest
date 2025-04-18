package com.bank_system.account_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {
    @NotBlank
    private String transactionType;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal value;

    @NotNull
    private Long accountId;
}
