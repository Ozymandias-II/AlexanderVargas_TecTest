package com.bank_system.client.dto;

import lombok.Data;

@Data
public class ClientResponseDTO {
    private Long clientId;
    private String name;
    private String gender;
    private String age;
    private String identification;
    private String address;
    private String phone;
    private Boolean status;
}
