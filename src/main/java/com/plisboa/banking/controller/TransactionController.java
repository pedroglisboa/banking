package com.plisboa.banking.controller;


import com.plisboa.banking.domain.entity.Transaction;
import com.plisboa.banking.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@Tag(name = "Transaction Controller", description = "Controlador para consulta das transações bancárias")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController
      (TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("/{id}/transactions")
  @Operation(summary = "Transações de um cliente em uma data", description = "Este endpoint retorna todas as transações de um cliente em uma determinada data")
  public Page<Transaction> transactionsByAccountAndDate(@PathVariable String id,
      @RequestParam(name = "transactionDate") LocalDate transactionDate,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return transactionService.findByAccountIdAndTransactionDate(id, transactionDate,
        pageRequest);
  }

  @GetMapping("/transactions")
  @Operation(summary = "Consultar todas as transações", description = "Este endpoint retorna todas as transações do sistema")
  public Page<Transaction> transactions(@RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return transactionService.findAllTransactions(pageRequest);
  }

}

