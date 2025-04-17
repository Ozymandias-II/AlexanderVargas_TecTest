package com.bank_system.account_service.service;

import com.bank_system.account_service.dto.*;
import com.bank_system.account_service.exception.AccountNotFoundException;
import com.bank_system.account_service.exception.DuplicateAccountException;
import com.bank_system.account_service.exception.InsufficientBalanceException;
import com.bank_system.account_service.model.Account;
import com.bank_system.account_service.model.Transaction;
import com.bank_system.account_service.repository.AccountRepository;
import com.bank_system.account_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO request) {
        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new DuplicateAccountException("Ya existe una cuenta con el número " + request.getAccountNumber());
        }

        Account account = modelMapper.map(request, Account.class);
        account = accountRepository.save(account);

        kafkaProducer.sendAccountCreatedEvent(account.getClientId(), account.getAccountId());

        return modelMapper.map(account, AccountResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("No existe cuenta con el id " + id));
        return modelMapper.map(account, AccountResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> modelMapper.map(account, AccountResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe cuenta con id " + id));
        modelMapper.map(request, account);
        account = accountRepository.save(account);
        return modelMapper.map(account, AccountResponseDTO.class);
    }

    @Transactional
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe cuenta con id " + id);
        }
        accountRepository.deleteById(id);
    }

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe cuenta con id " + request.getAccountId()));

        BigDecimal newBalance;
        if (request.getTransactionType().equalsIgnoreCase("DEPOSITO")) {
            newBalance = account.getInitialBalance().add(request.getValue());
        } else if (request.getTransactionType().equalsIgnoreCase("RETIRO")) {
            if (account.getInitialBalance().compareTo(request.getValue()) < 0) {
                throw new InsufficientBalanceException("Saldo no Disponible");
            }
            newBalance = account.getInitialBalance().subtract(request.getValue());
        } else {
            throw new IllegalArgumentException("Tipo de transacción inválido: " + request.getTransactionType());
        }
        Transaction transaction = modelMapper.map(request, Transaction.class);
        transaction = transactionRepository.save(transaction);

        account.setInitialBalance(newBalance);
        accountRepository.save(account);

        return modelMapper.map(transaction, TransactionResponseDTO.class);

    }

    @Transactional(readOnly = true)
    public ClientStatementDTO getClientStatement(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Account> accounts = accountRepository.findByClientId(clientId);

        ClientStatementDTO statement = new ClientStatementDTO();
        statement.setClientId(clientId);

        List<AccountStatementDTO> accountStatements = accounts.stream().map(account -> {
            AccountStatementDTO accountStatementDTO = new AccountStatementDTO();
            accountStatementDTO.setAccount(modelMapper.map(account, AccountResponseDTO.class));
            List<Transaction> transactions = transactionRepository.
                    findByAccountIdAndDateBetween(account.getAccountId(), startDate, endDate);
            accountStatementDTO.setTransactions(transactions.stream()
                    .map(t -> modelMapper.map(t, TransactionResponseDTO.class))
                    .collect(Collectors.toList()));
            if (!transactions.isEmpty()) {
                BigDecimal initial = transactions.get(0).getBalance().subtract(transactions.get(0).getValue());
                BigDecimal finalBalance = transactions.get(transactions.size() - 1).getBalance();
                accountStatementDTO.setInitialBalance(initial);
                accountStatementDTO.setFinalBalance(finalBalance);
            } else {
                accountStatementDTO.setInitialBalance(account.getInitialBalance());
                accountStatementDTO.setFinalBalance(account.getInitialBalance());
            }
            return accountStatementDTO;
        }).collect(Collectors.toList());
        statement.setAccounts(accountStatements);
        return statement;
    }
}
