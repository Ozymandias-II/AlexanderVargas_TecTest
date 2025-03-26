package com.bank_system.account_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountType;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal initialBalance;

    @NotNull
    private Boolean status;

    @NotNull
    private Long clientId;
}
