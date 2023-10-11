package com.plisboa.banking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.plisboa.banking.entity.Transaction;
import com.plisboa.banking.service.TransactionService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class TransactionControllerTest {

  @InjectMocks
  private TransactionController transactionController;

  @Mock
  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    // Configurações adicionais podem ser adicionadas aqui, se necessário
  }

  @Test
  void testTransactionsByAccountAndDate() {
    String clientId = "test123";
    LocalDate transactionDate = LocalDate.of(2023, 9, 1);
    Transaction transaction1 = new Transaction();
    transaction1.setAccountId(clientId);
    transaction1.setTransactionDate(transactionDate);
    Transaction transaction2 = new Transaction();
    transaction2.setAccountId(clientId);
    transaction2.setTransactionDate(transactionDate);
    List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

    // Crie uma nova instância de Page<Client> com os elementos
    Pageable pageable = PageRequest.of(0, 10);
    Page<Transaction> page = new PageImpl<>(transactions, pageable, transactions.size());

    PageRequest pageRequest = PageRequest.of(0, 10);

    when(
        transactionService.findByAccountIdAndTransactionDate(clientId, transactionDate,
            pageRequest)).thenReturn(
        page);

    Page<Transaction> result = transactionController.transactionsByAccountAndDate(clientId,
        transactionDate, 0, 10);

    assertEquals(2, result.getTotalElements());
  }

  @Test
  void testTransactions() {
    Transaction transaction1 = new Transaction();
    Transaction transaction2 = new Transaction();
    List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

    // Crie uma nova instância de Page<Client> com os elementos
    Pageable pageable = PageRequest.of(0, 10);
    Page<Transaction> page = new PageImpl<>(transactions, pageable, transactions.size());

    PageRequest pageRequest = PageRequest.of(0, 10);

    when(transactionService.findAllTransactions(pageRequest)).thenReturn(page);

    Page<Transaction> result = transactionController.transactions(0, 10);

    assertEquals(2, result.getTotalElements());
  }
}

