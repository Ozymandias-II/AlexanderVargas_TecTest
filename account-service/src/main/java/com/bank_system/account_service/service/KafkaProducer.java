package com.bank_system.account_service.service;

import com.bank_system.account_service.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private static  final String TOPIC = "account-created";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendAccountCreatedEvent(Long clientId, Long accountId){
        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setClientId(clientId);
        event.setAccountId(accountId);
        event.setCreatedAt(LocalDateTime.now());

        kafkaTemplate.send(TOPIC, event);
    }
}
