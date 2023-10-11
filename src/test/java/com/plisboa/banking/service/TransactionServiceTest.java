package com.plisboa.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.plisboa.banking.entity.Transaction;
import com.plisboa.banking.repository.TransactionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionServiceTest {

  @InjectMocks
  private TransactionService transactionService;

  @Mock
  private TransactionRepository transactionRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAllTransactions() {
    // Configuração do cenário de teste
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction());
    transactions.add(new Transaction());
    when(transactionRepository.findAll()).thenReturn(transactions);

    // Executa o método de teste
    List<Transaction> foundTransactions = transactionService.findAllTransactions();

    // Verifica o resultado
    assertEquals(2, foundTransactions.size());
  }

  @Test
  void testCreateTransaction() {
    // Configuração do cenário de teste
    Transaction transactionToCreate = new Transaction();
    when(transactionRepository.save(transactionToCreate)).thenReturn(transactionToCreate);

    // Executa o método de teste
    transactionService.createTransaction(transactionToCreate);

    // Verifica se o método save foi chamado no repository
    verify(transactionRepository, times(1)).save(transactionToCreate);
  }

  @Test
  void testFindByAccountIdAndTransactionDate() {
    // Configuração do cenário de teste
    String accountId = "account123";
    LocalDate transactionDate = LocalDate.now();
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction());
    transactions.add(new Transaction());
    when(transactionRepository.findByAccountIdAndTransactionDate(accountId,
        transactionDate)).thenReturn(transactions);

    // Executa o método de teste
    List<Transaction> foundTransactions = transactionService.findByAccountIdAndTransactionDate(
        accountId, transactionDate);

    // Verifica o resultado
    assertEquals(2, foundTransactions.size());
  }
}

