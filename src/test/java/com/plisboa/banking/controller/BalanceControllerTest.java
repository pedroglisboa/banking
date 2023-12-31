package com.plisboa.banking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.plisboa.banking.request.BalanceRequest;
import com.plisboa.banking.service.BalanceService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class BalanceControllerTest {

  @InjectMocks
  private BalanceController balanceController;

  @Mock
  private BalanceService balanceService;

  @BeforeEach
  void setUp() {
    // Você pode adicionar configurações adicionais aqui, se necessário
  }

  @Test
  void testWithdraw() {
    String clientId = "test123";
    BigDecimal withdrawAmount = new BigDecimal("100.00");
    BalanceRequest balanceRequest = new BalanceRequest(withdrawAmount);
    when(balanceService.withdraw(clientId, balanceRequest)).thenReturn(
        ResponseEntity.ok("Withdraw successful"));

    ResponseEntity<String> result = balanceController.withdraw(clientId, balanceRequest);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Withdraw successful", result.getBody());
  }

  @Test
  void testDeposit() {
    String clientId = "test123";
    BigDecimal depositAmount = new BigDecimal("200.00");
    BalanceRequest balanceRequest = new BalanceRequest(depositAmount);
    when(balanceService.deposit(clientId, balanceRequest)).thenReturn(
        ResponseEntity.ok("Deposit successful"));

    ResponseEntity<String> result = balanceController.deposit(clientId, balanceRequest);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Deposit successful", result.getBody());
  }
}
