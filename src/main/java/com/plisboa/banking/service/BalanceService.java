package com.plisboa.banking.service;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.entity.Transaction;
import com.plisboa.banking.exception.NoBalanceException;
import com.plisboa.banking.repository.ClientRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

  private static final BigDecimal MIN_VALUE = new BigDecimal(100);
  private static final BigDecimal MAX_VALUE = new BigDecimal(300);

  private static final BigDecimal TAX_MIN = BigDecimal.valueOf(1.004);

  private static final BigDecimal TAX_MAX = BigDecimal.valueOf(1.01);

  private static final String DEPOSIT = "deposito";
  private static final String WITHDRAW = "saque";
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

      try {
        newBalance = withdraw(client, withdraw);
        client.setBalance(newBalance);
        transactionService.createTransaction(
            buildTransaction(client.getAccountId(), WITHDRAW, withdraw));
      } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body("Sem Saldo");
      }

      clientRepository.save(client);
      return ResponseEntity.ok().body("Seu novo saldo é: " + newBalance);
    } else {
      return ResponseEntity.notFound().build();
    }

  }

  public ResponseEntity<String> deposit(String id, BigDecimal deposit) {

    Optional<Client> clientOptional = clientRepository.findById(id);

    Client client;
    if (clientOptional.isPresent()) {
      client = clientOptional.get();
      BigDecimal newBalance;

      newBalance = client.getBalance().add(deposit);
      client.setBalance(newBalance);
      transactionService.createTransaction(
          buildTransaction(client.getAccountId(), DEPOSIT, deposit));
      clientRepository.save(client);
      return ResponseEntity.ok().body("Seu novo saldo é: " + newBalance);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  private BigDecimal withdraw(Client client, BigDecimal withdraw) {

    BigDecimal newBalance;

    if (Boolean.TRUE.equals(client.getIsPrimeExclusive())) {
      newBalance = client.getBalance().subtract(withdraw);
    } else if (withdraw.compareTo(MIN_VALUE) <= 0) {
      newBalance = client.getBalance().subtract(withdraw);
    } else if (withdraw.compareTo(MAX_VALUE) > 0) {
      newBalance = client.getBalance().subtract(withdraw.multiply(TAX_MAX));
    } else {
      newBalance = client.getBalance().subtract(withdraw.multiply(TAX_MIN));
    }

    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new NoBalanceException("Sem saldo");
    }
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
