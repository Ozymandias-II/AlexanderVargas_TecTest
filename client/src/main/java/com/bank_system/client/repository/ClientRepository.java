package com.bank_system.client.repository;

import com.bank_system.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByPersonIdentification(String identificaction);

    Client findByPersonIdentification(String identification);
}
