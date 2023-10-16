package com.plisboa.banking.service;

import com.plisboa.banking.domain.entity.Client;
import com.plisboa.banking.domain.entity.Transaction;
import com.plisboa.banking.domain.repository.ClientRepository;
import com.plisboa.banking.exception.NoBalanceBadRequestException;
import com.plisboa.banking.utils.BankingConstants;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

  private final Logger logger = LoggerFactory.getLogger(BalanceService.class);
  private final ClientRepository clientRepository;
  private final TransactionService transactionService;

  public BalanceService(ClientRepository clientRepository, TransactionService transactionService) {
    this.clientRepository = clientRepository;
    this.transactionService = transactionService;
  }

  public ResponseEntity<String> withdraw(String id, BigDecimal withdraw) {

    Optional<Client> clientOptional = clientRepository.findById(id);

    Client client;
    if (clientOptional.isPresent()) {
      client = clientOptional.get();
      BigDecimal newBalance;

      newBalance = withdraw(client, withdraw
      );
      client.setBalance(newBalance);
      Transaction transaction = transactionService.createTransaction(
          buildTransaction(client.getAccountId(), BankingConstants.WITHDRAW_STRING, withdraw));

      logger.info("Transaction: {}", transaction);

      clientRepository.save(client);
      return ResponseEntity.ok().body("Seu novo saldo é: " + newBalance);
    } else {
      throw new EntityNotFoundException("Cliente não encontrado");
    }

  }

  public ResponseEntity<String> deposit(String id, BigDecimal deposit) {

    Optional<Client> clientOptional = clientRepository.findById(id);

    Client client;
    if (clientOptional.isPresent()) {
      client = clientOptional.get();
      BigDecimal newBalance;
      logger.info("Balance: {}", client.getBalance());
      logger.info("Depósito: +{}", deposit);
      newBalance = client.getBalance().add(deposit);
      client.setBalance(newBalance);
      Transaction transaction = transactionService.createTransaction(
          buildTransaction(client.getAccountId(), BankingConstants.DEPOSIT_STRING, deposit));
      logger.info("Transaction: {}", transaction);
      clientRepository.save(client);
      logger.info("Novo saldo: {}", newBalance);
      return ResponseEntity.ok().body("Seu novo saldo é: " + newBalance);
    } else {
      throw new EntityNotFoundException("Cliente não encontrado");
    }
  }

  private BigDecimal withdraw(Client client, BigDecimal withdraw) {

    BigDecimal newBalance;
    logger.info("Balance: {}", client.getBalance());
    logger.info("Saque: -{}", withdraw);

    //is prime
    if (Boolean.TRUE.equals(client.getIsPrimeExclusive())) {
      logger.info("Taxa: Exclusive Prime");
      newBalance = client.getBalance().subtract(withdraw);
      // less than minimum - free
    } else if (withdraw.compareTo(BankingConstants.MIN_VALUE) <= 0) {
      logger.info("Taxa: Livre");
      newBalance = client.getBalance().subtract(withdraw);
      // greater or equal than minimum and less than maximum
    } else if (withdraw.compareTo(BankingConstants.MIN_VALUE) > 0 && withdraw.compareTo(
        BankingConstants.MAX_VALUE) <= 0) {
      logger.info("Taxa: {}", BankingConstants.MIN_TAX);
      newBalance = client.getBalance().subtract(withdraw.add(withdraw.multiply(
          BankingConstants.MIN_TAX)));
      //greater than maximum
    } else {
      logger.info("Taxa: {}", BankingConstants.MAX_TAX);
      newBalance = client.getBalance().subtract(withdraw.add(withdraw.multiply(
          BankingConstants.MAX_TAX)));
    }

    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new NoBalanceBadRequestException("Sem saldo suficiente...");
    }
    logger.info("Novo saldo: {}", newBalance);
    return newBalance;
  }

  private Transaction buildTransaction(String accountId, String type, BigDecimal value) {
    Transaction transaction = new Transaction();
    transaction.setTransactionValue(value);
    transaction.setType(type);
    transaction.setAccountId(accountId);
    transaction.setTransactionDate(LocalDate.now());
    return transaction;
  }

}
