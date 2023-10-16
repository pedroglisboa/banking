package com.plisboa.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.plisboa.banking.domain.entity.Client;
import com.plisboa.banking.domain.entity.Transaction;
import com.plisboa.banking.domain.repository.ClientRepository;
import com.plisboa.banking.exception.NoBalanceBadRequestException;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class BalanceServiceTest {

  @InjectMocks
  private BalanceService balanceService;

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testWithdraw() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("100.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("500.00"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    ResponseEntity<String> result = balanceService.withdraw(clientId, withdrawAmount);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Seu novo saldo é: 400.00", result.getBody());
  }

  @Test
  void testWithdraw_NotEnoughBalance() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("600.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("500.00"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    assertThrows(NoBalanceBadRequestException.class,
        () -> balanceService.withdraw(clientId, withdrawAmount));
  }

  @Test
  void testWithdraw_Prime() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("15.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("100.00"));
    client.setIsPrimeExclusive(true);

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    ResponseEntity<String> result = balanceService.withdraw(clientId, withdrawAmount);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Seu novo saldo é: 85.00", result.getBody());
  }

  @Test
  void testWithdraw_MaxTax() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("300.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("500.00"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    ResponseEntity<String> result = balanceService.withdraw(clientId, withdrawAmount);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Seu novo saldo é: 198.80000", result.getBody());
  }

  @Test
  void testWithdraw_MinTax() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("15.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("100.00"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    ResponseEntity<String> result = balanceService.withdraw(clientId, withdrawAmount);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Seu novo saldo é: 85.00", result.getBody());
  }

  @Test
  void testWithdraw_Exception() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("200.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("100.00"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    // Use assertThrows para verificar se a exceção é lançada
    assertThrows(NoBalanceBadRequestException.class,
        () -> balanceService.withdraw(clientId, withdrawAmount));

  }

  @Test
  void testWithdraw_NotFound() {
    String clientId = "nonExistentId";
    BigDecimal withdrawAmount = new BigDecimal("100.00");

    when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    assertThrows(EntityNotFoundException.class,
        () -> balanceService.withdraw(clientId, withdrawAmount));
  }

  @Test
  void testDeposit() {
    String clientId = "test123";
    BigDecimal depositAmount = new BigDecimal("100.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("500.00"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    ResponseEntity<String> result = balanceService.deposit(clientId, depositAmount);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Seu novo saldo é: 600.00", result.getBody());
  }

  @Test
  void testDeposit_NotFound() {
    String clientId = "nonExistentId";
    BigDecimal depositAmount = new BigDecimal("100.00");

    when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    assertThrows(EntityNotFoundException.class,
        () -> balanceService.deposit(clientId, depositAmount));
  }
}
