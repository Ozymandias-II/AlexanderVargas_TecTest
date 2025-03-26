package com.bank_system.account_service.config;

import com.bank_system.account_service.dto.AccountRequestDTO;
import com.bank_system.account_service.dto.TransactionRequestDTO;
import com.bank_system.account_service.model.Account;
import com.bank_system.account_service.model.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuración para mapeo estricto (opcional pero recomendado)
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Configuraciones personalizadas para tus entidades
        configureAccountMappings(modelMapper);
        configureTransactionMappings(modelMapper);

        return modelMapper;
    }

    private void configureAccountMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(AccountRequestDTO.class, Account.class)
                .addMappings(mapper -> {
                    mapper.map(AccountRequestDTO::getAccountNumber, Account::setAccountNumber);
                    mapper.map(AccountRequestDTO::getAccountType, Account::setAccountType);
                    mapper.map(AccountRequestDTO::getInitialBalance, Account::setInitialBalance);
                    mapper.map(src -> true, Account::setStatus); // Valor por defecto para status
                });
    }

    private void configureTransactionMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(TransactionRequestDTO.class, Transaction.class)
                .addMappings(mapper -> {
                    mapper.map(TransactionRequestDTO::getTransactionType, Transaction::setTransactionType);
                    mapper.map(TransactionRequestDTO::getValue, Transaction::setValue);
                    mapper.map(src -> LocalDateTime.now(), Transaction::setDate); // Fecha actual por defecto
                });
    }
}
