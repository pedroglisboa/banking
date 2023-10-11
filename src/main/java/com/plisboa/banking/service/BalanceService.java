package com.plisboa.banking.service;

import static com.plisboa.banking.utils.BankingConstants.MAX_TAX_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MAX_VALUE_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MIN_TAX_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MIN_VALUE_REDIS;
import static com.plisboa.banking.utils.BankingConstants.WITHDRAW_REDIS;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.entity.Param;
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

  private final ClientRepository clientRepository;
  private final TransactionService transactionService;
  private final ParamService paramService;

  public BalanceService(ClientRepository clientRepository, TransactionService transactionService,
      ParamService paramService) {
    this.clientRepository = clientRepository;
    this.transactionService = transactionService;
    this.paramService = paramService;
  }

  public ResponseEntity<String> withdraw(String id, BigDecimal withdraw) {
    Param withdrawParam = paramService.findParam(WITHDRAW_REDIS);
    Param minValue = paramService.findParam(MIN_VALUE_REDIS);
    Param maxValue = paramService.findParam(MAX_VALUE_REDIS);
    Param minTax = paramService.findParam(MIN_TAX_REDIS);
    Param maxTax = paramService.findParam(MAX_TAX_REDIS);

    Optional<Client> clientOptional = clientRepository.findById(id);

    Client client;
    if (clientOptional.isPresent()) {
      client = clientOptional.get();
      BigDecimal newBalance;

      try {
        newBalance = withdraw(client, withdraw, minValue.getValue(), maxValue.getValue(), minTax.getValue(), maxTax.getValue());
        client.setBalance(newBalance);
        transactionService.createTransaction(
            buildTransaction(client.getAccountId(), withdrawParam.getStringValue(), withdraw));
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

    Param depositParam = paramService.findParam(WITHDRAW_REDIS);

    Optional<Client> clientOptional = clientRepository.findById(id);

    Client client;
    if (clientOptional.isPresent()) {
      client = clientOptional.get();
      BigDecimal newBalance;

      newBalance = client.getBalance().add(deposit);
      client.setBalance(newBalance);
      transactionService.createTransaction(
          buildTransaction(client.getAccountId(), depositParam.getStringValue(), deposit));
      clientRepository.save(client);
      return ResponseEntity.ok().body("Seu novo saldo é: " + newBalance);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  private BigDecimal withdraw(Client client, BigDecimal withdraw, BigDecimal minValue,
      BigDecimal maxValue, BigDecimal taxMin, BigDecimal taxMax) {

    BigDecimal newBalance;
    //is prime
    if (Boolean.TRUE.equals(client.getIsPrimeExclusive())) {
      newBalance = client.getBalance().subtract(withdraw);
      // less than minimum - free
    } else if (withdraw.compareTo(minValue) <= 0) {
      newBalance = client.getBalance().subtract(withdraw);
      // greater or equal than minimum and less than maximum
    } else if (withdraw.compareTo(minValue) > 0 && withdraw.compareTo(maxValue) <= 0) {
      newBalance = client.getBalance().subtract(withdraw.add(withdraw.multiply(taxMax)));
      //greater than maximum
    } else {
      newBalance = client.getBalance().subtract(withdraw.add(withdraw.multiply(taxMin)));
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
