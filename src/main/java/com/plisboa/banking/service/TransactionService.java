package com.plisboa.banking.service;

import com.plisboa.banking.entity.Transaction;
import com.plisboa.banking.repository.TransactionRepository;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;

  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public Page<Transaction> findAllTransactions(PageRequest pageRequest) {
    return transactionRepository.findAll(pageRequest);
  }

  public Transaction createTransaction(@RequestBody Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public Page<Transaction> findByAccountIdAndTransactionDate(String accountId,
      LocalDate transactionDate, PageRequest pageRequest) {
    return transactionRepository.findByAccountIdAndTransactionDate(accountId, transactionDate,
        pageRequest);
  }
}
