package com.plisboa.banking.repository;

import com.plisboa.banking.entity.Transaction;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

  Page<Transaction> findByAccountIdAndTransactionDate(String accountId, LocalDate transactionDate,
      PageRequest pageRequest);
}
