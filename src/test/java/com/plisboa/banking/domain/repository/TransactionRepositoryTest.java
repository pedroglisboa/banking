package com.plisboa.banking.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.plisboa.banking.domain.entity.Transaction;
import com.plisboa.banking.domain.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class TransactionRepositoryTest {

  @Autowired
  private TransactionRepository transactionRepository;

  @Test
  void testFindByAccountIdAndTransactionDate() {
    Transaction transaction1 = new Transaction();
    transaction1.setAccountId("account1");
    transaction1.setType("credit");
    transaction1.setTransactionValue(new BigDecimal("100.00"));
    transaction1.setTransactionDate(LocalDate.now());

    Transaction transaction2 = new Transaction();
    transaction2.setAccountId("account1");
    transaction2.setType("debit");
    transaction2.setTransactionValue(new BigDecimal("50.00"));
    transaction2.setTransactionDate(LocalDate.now());

    Transaction transaction3 = new Transaction();
    transaction3.setAccountId("account2");
    transaction3.setType("credit");
    transaction3.setTransactionValue(new BigDecimal("200.00"));
    transaction3.setTransactionDate(LocalDate.now());

    transactionRepository.save(transaction1);
    transactionRepository.save(transaction2);
    transactionRepository.save(transaction3);

    PageRequest pageRequest = PageRequest.of(0, 10);

    Page<Transaction> transactionsForAccount1 = transactionRepository.findByAccountIdAndTransactionDate(
        "account1", LocalDate.now(), pageRequest);

    assertEquals(2, transactionsForAccount1.getTotalElements());
    assertTrue(transactionsForAccount1.getContent().contains(transaction1));
    assertTrue(transactionsForAccount1.getContent().contains(transaction2));
  }
}

