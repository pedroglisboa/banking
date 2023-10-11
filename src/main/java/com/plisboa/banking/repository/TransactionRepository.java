package com.plisboa.banking.repository;

import com.plisboa.banking.entity.Transaction;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

  List<Transaction> findByAccountIdAndTransactionDate(String accountId, LocalDate transactionDate);
}
