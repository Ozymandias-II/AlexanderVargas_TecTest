package com.bank_system.client.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @Column(name = "person_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @NotNull(message = "Person must not be null")
    private Person person;

    @Column(nullable = false)
    @NotNull(message = "Password must not be null")
    private String password;

    @Column(nullable = false)

    @NotNull(message = "Status must not be null")
    private Boolean status;
}
