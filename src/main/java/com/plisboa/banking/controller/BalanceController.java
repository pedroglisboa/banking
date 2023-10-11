package com.plisboa.banking.controller;


import com.plisboa.banking.service.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@Tag(name = "Balance Controller", description = "Controlador para operações bancárias de clientes")
public class BalanceController {

  private final BalanceService balanceService;

  public BalanceController
      (BalanceService balanceService) {
    this.balanceService = balanceService;
  }

  @PutMapping("/{id}/withdraw")
  @Operation(summary = "Sacar Saldo", description = "Este endpoint executa uma operação de saque do cliente contando uma taxa de administração")
  public ResponseEntity<String> withdraw(@PathVariable String id,
      @RequestBody BigDecimal withdraw) {
    return balanceService.withdraw(id, withdraw);
  }

  @PutMapping("/{id}/deposit")
  @Operation(summary = "Depositar valor", description = "Este endpoint executa uma operação de deposito para o cliente")
  public ResponseEntity<String> deposit(@PathVariable String id,
      @RequestBody BigDecimal deposit) {
    return balanceService.deposit(id, deposit);
  }

}

