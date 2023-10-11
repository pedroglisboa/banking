package com.plisboa.banking.service;

import static com.plisboa.banking.utils.BankingConstants.DEPOSIT_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MAX_TAX_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MAX_VALUE_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MIN_TAX_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MIN_VALUE_REDIS;
import static com.plisboa.banking.utils.BankingConstants.WITHDRAW_REDIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.entity.Param;
import com.plisboa.banking.entity.Transaction;
import com.plisboa.banking.exception.NoBalanceBadRequestException;
import com.plisboa.banking.repository.ClientRepository;
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

  @Mock
  private ParamService paramService;

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
    Param withdrawParam = new Param();
    withdrawParam.setStringValue("withdraw");
    Param minValue = new Param();
    minValue.setValue(new BigDecimal("10.00"));
    Param maxValue = new Param();
    maxValue.setValue(new BigDecimal("200.00"));
    Param minTax = new Param();
    minTax.setValue(new BigDecimal("0.05"));
    Param maxTax = new Param();
    maxTax.setValue(new BigDecimal("0.10"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(paramService.findParam(WITHDRAW_REDIS)).thenReturn(withdrawParam);
    when(paramService.findParam(MIN_VALUE_REDIS)).thenReturn(minValue);
    when(paramService.findParam(MAX_VALUE_REDIS)).thenReturn(maxValue);
    when(paramService.findParam(MIN_TAX_REDIS)).thenReturn(minTax);
    when(paramService.findParam(MAX_TAX_REDIS)).thenReturn(maxTax);
    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    ResponseEntity<String> result = balanceService.withdraw(clientId, withdrawAmount);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Seu novo saldo é: 395.0000", result.getBody());
  }

  @Test
  void testWithdraw_NotEnoughBalance() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("600.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("500.00"));
    Param withdrawParam = new Param();
    withdrawParam.setStringValue("withdraw");
    Param minValue = new Param();
    minValue.setValue(new BigDecimal("10.00"));
    Param maxValue = new Param();
    maxValue.setValue(new BigDecimal("200.00"));
    Param minTax = new Param();
    minTax.setValue(new BigDecimal("0.05"));
    Param maxTax = new Param();
    maxTax.setValue(new BigDecimal("0.10"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(paramService.findParam(WITHDRAW_REDIS)).thenReturn(withdrawParam);
    when(paramService.findParam(MIN_VALUE_REDIS)).thenReturn(minValue);
    when(paramService.findParam(MAX_VALUE_REDIS)).thenReturn(maxValue);
    when(paramService.findParam(MIN_TAX_REDIS)).thenReturn(minTax);
    when(paramService.findParam(MAX_TAX_REDIS)).thenReturn(maxTax);
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
    Param withdrawParam = new Param();
    withdrawParam.setStringValue("withdraw");
    Param minValue = new Param();
    minValue.setValue(new BigDecimal("20.00"));
    Param maxValue = new Param();
    maxValue.setValue(new BigDecimal("200.00"));
    Param minTax = new Param();
    minTax.setValue(new BigDecimal("0.05"));
    Param maxTax = new Param();
    maxTax.setValue(new BigDecimal("0.10"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(paramService.findParam(WITHDRAW_REDIS)).thenReturn(withdrawParam);
    when(paramService.findParam(MIN_VALUE_REDIS)).thenReturn(minValue);
    when(paramService.findParam(MAX_VALUE_REDIS)).thenReturn(maxValue);
    when(paramService.findParam(MIN_TAX_REDIS)).thenReturn(minTax);
    when(paramService.findParam(MAX_TAX_REDIS)).thenReturn(maxTax);

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
    Param withdrawParam = new Param();
    withdrawParam.setStringValue("withdraw");
    Param minValue = new Param();
    minValue.setValue(new BigDecimal("10.00"));
    Param maxValue = new Param();
    maxValue.setValue(new BigDecimal("200.00"));
    Param minTax = new Param();
    minTax.setValue(new BigDecimal("0.05"));
    Param maxTax = new Param();
    maxTax.setValue(new BigDecimal("0.10"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(paramService.findParam(WITHDRAW_REDIS)).thenReturn(withdrawParam);
    when(paramService.findParam(MIN_VALUE_REDIS)).thenReturn(minValue);
    when(paramService.findParam(MAX_VALUE_REDIS)).thenReturn(maxValue);
    when(paramService.findParam(MIN_TAX_REDIS)).thenReturn(minTax);
    when(paramService.findParam(MAX_TAX_REDIS)).thenReturn(maxTax);
    when(transactionService.createTransaction(any())).thenReturn(new Transaction());

    ResponseEntity<String> result = balanceService.withdraw(clientId, withdrawAmount);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Seu novo saldo é: 170.0000", result.getBody());
  }

  @Test
  void testWithdraw_MinTax() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("15.00");
    Client client = new Client();
    client.setAccountId(clientId);
    client.setBalance(new BigDecimal("100.00"));
    Param withdrawParam = new Param();
    withdrawParam.setStringValue("withdraw");
    Param minValue = new Param();
    minValue.setValue(new BigDecimal("20.00"));
    Param maxValue = new Param();
    maxValue.setValue(new BigDecimal("200.00"));
    Param minTax = new Param();
    minTax.setValue(new BigDecimal("0.05"));
    Param maxTax = new Param();
    maxTax.setValue(new BigDecimal("0.10"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(paramService.findParam(WITHDRAW_REDIS)).thenReturn(withdrawParam);
    when(paramService.findParam(MIN_VALUE_REDIS)).thenReturn(minValue);
    when(paramService.findParam(MAX_VALUE_REDIS)).thenReturn(maxValue);
    when(paramService.findParam(MIN_TAX_REDIS)).thenReturn(minTax);
    when(paramService.findParam(MAX_TAX_REDIS)).thenReturn(maxTax);
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
    Param withdrawParam = new Param();
    withdrawParam.setStringValue("withdraw");
    Param minValue = new Param();
    minValue.setValue(new BigDecimal("10.00"));
    Param maxValue = new Param();
    maxValue.setValue(new BigDecimal("200.00"));
    Param minTax = new Param();
    minTax.setValue(new BigDecimal("0.05"));
    Param maxTax = new Param();
    maxTax.setValue(new BigDecimal("0.10"));

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    when(paramService.findParam(WITHDRAW_REDIS)).thenReturn(withdrawParam);
    when(paramService.findParam(MIN_VALUE_REDIS)).thenReturn(minValue);
    when(paramService.findParam(MAX_VALUE_REDIS)).thenReturn(maxValue);
    when(paramService.findParam(MIN_TAX_REDIS)).thenReturn(minTax);
    when(paramService.findParam(MAX_TAX_REDIS)).thenReturn(maxTax);
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
    Param depositParam = new Param();
    depositParam.setStringValue("Deposit");

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(paramService.findParam(DEPOSIT_REDIS)).thenReturn(depositParam);
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
