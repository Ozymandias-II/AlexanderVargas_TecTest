package com.bank_system.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequestDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String gender;
    @NotNull
    private Integer age;
    @NotBlank
    private String identification;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
    @NotNull
    private Boolean status;

}
