package com.plisboa.banking.controller;


import com.plisboa.banking.entity.Client;
import com.plisboa.banking.entity.Transaction;
import com.plisboa.banking.service.BalanceService;
import com.plisboa.banking.service.ClientService;
import com.plisboa.banking.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@Tag(name = "Baking Controller", description = "Controlador para operações bancárias de clientes")
public class BankingController {

  private final ClientService clientService;
  private final BalanceService balanceService;
  private final TransactionService transactionService;

  public BankingController
      (ClientService clientService, BalanceService balanceService,
          TransactionService transactionService) {
    this.clientService = clientService;
    this.balanceService = balanceService;
    this.transactionService = transactionService;
  }

  @GetMapping
  @Operation(summary = "Pegar todos os Clientes", description = "Este endpoint retorna todos os clientes.")
  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Pegar Cliente por AccountId", description = "Este endpoint retorna um cliente pelo AccountId.")
  public ResponseEntity<Client> getClientById(@PathVariable String id) {
    return clientService.getClientById(id);
  }

  @PostMapping
  @Operation(summary = "Criar Cliente", description = "Este endpoint cria um cliente.")
  public Client createClient(@RequestBody Client client) {
    return clientService.createClient(client);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Alterar Cliente", description = "Este endpoint edita um cliente filtrado pelo AccountId.")
  public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client client) {
    return clientService.updateClient(id, client);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deletar Cliente", description = "Este endpoint remnove um cliente filtrado pelo AccountId.")
  public ResponseEntity<Void> deleteClient(@PathVariable String id) {
    return clientService.deleteClient(id);
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

  @PutMapping("/{id}/transactions")
  @Operation(summary = "Transações de um cliente em uma data", description = "Este endpoint retorna todas as transações de um cliente em uma determinada data")
  public List<Transaction> transactionsByAccountAndDate(@PathVariable String id,
      @RequestBody LocalDate transactionDate) {
    return transactionService.findByAccountIdAndTransactionDate(id, transactionDate);
  }

  @PutMapping("/transactions")
  @Operation(summary = "Consultar todas as transações", description = "Este endpoint retorna todas as transações do sistema")
  public List<Transaction> transactions() {
    return transactionService.findAllTransactions();
  }

}

