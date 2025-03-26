package com.bank_system.client.repository;

import com.bank_system.client.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByIdentification(String identification);
    Person findByIdentification(String identification);
}
