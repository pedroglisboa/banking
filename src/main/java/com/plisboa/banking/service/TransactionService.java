package com.plisboa.banking.service;

import com.plisboa.banking.entity.Transaction;
import com.plisboa.banking.repository.TransactionRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;

  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public List<Transaction> findAllTransactions(){
   return transactionRepository.findAll();
  }

  public Transaction createTransaction(@RequestBody Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public List<Transaction> findByAccountIdAndTransactionDate(String accountId, LocalDate transactionDate) {
    return transactionRepository.findByAccountIdAndTransactionDate(accountId, transactionDate);
  }
}
