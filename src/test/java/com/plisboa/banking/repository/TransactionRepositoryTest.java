package com.plisboa.banking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.plisboa.banking.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    List<Transaction> transactionsForAccount1 = transactionRepository.findByAccountIdAndTransactionDate(
        "account1", LocalDate.now());

    assertEquals(2, transactionsForAccount1.size());
    assertTrue(transactionsForAccount1.contains(transaction1));
    assertTrue(transactionsForAccount1.contains(transaction2));
  }
}

