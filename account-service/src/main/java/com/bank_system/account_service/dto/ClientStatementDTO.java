package com.bank_system.account_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientStatementDTO {
    private Long clientId;
    private List<AccountStatementDTO> accounts;
}
