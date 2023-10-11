package com.plisboa.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.Generated;


@Data
@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String transactionId;
  private String accountId;
  private String type;
  private BigDecimal transactionValue;
  private LocalDate transactionDate;

  public Transaction() {
  }

}