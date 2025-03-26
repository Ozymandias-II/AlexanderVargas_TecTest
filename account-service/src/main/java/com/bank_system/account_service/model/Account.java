package com.bank_system.account_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(unique = true)
    private String accountNumber;

    private String accountType;

    @DecimalMin(value = "0.00", message = "Saldo inicial no puede ser negativo")
    private BigDecimal initialBalance;

    private Boolean status;

    private Long clientId;
}
