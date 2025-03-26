package com.bank_system.account_service.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountCreatedEvent {

    private Long clientId;
    private Long accountId;
    private LocalDateTime createdAt;
}
