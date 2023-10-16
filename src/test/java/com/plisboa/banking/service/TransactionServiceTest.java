package com.plisboa.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.plisboa.banking.domain.entity.Transaction;
import com.plisboa.banking.domain.repository.TransactionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    // Crie uma nova instância de Page<Client> com os elementos
    Pageable pageable = PageRequest.of(0, 10);
    Page<Transaction> page = new PageImpl<>(transactions, pageable, transactions.size());

    PageRequest pageRequest = PageRequest.of(0, 10);
    when(transactionRepository.findAll(pageRequest)).thenReturn(page);

    // Executa o método de teste
    Page<Transaction> foundTransactions = transactionService.findAllTransactions(pageRequest);

    // Verifica o resultado
    assertEquals(2, foundTransactions.getTotalElements());
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

    // Crie uma nova instância de Page<Client> com os elementos
    Pageable pageable = PageRequest.of(0, 10);
    Page<Transaction> page = new PageImpl<>(transactions, pageable, transactions.size());

    PageRequest pageRequest = PageRequest.of(0, 10);

    when(transactionRepository.findByAccountIdAndTransactionDate(accountId,
        transactionDate, pageRequest)).thenReturn(page);

    // Executa o método de teste
    Page<Transaction> foundTransactions = transactionService.findByAccountIdAndTransactionDate(
        accountId, transactionDate, pageRequest);

    // Verifica o resultado
    assertEquals(2, foundTransactions.getTotalElements());
  }
}

