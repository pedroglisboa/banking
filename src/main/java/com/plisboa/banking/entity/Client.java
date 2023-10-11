package com.plisboa.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
@Entity
public class Client {

  @Id
  private String accountId;
  private String name;
  private Boolean isPrimeExclusive;
  private BigDecimal balance;
  private Date birthDate;

  public Client() {
  }

}
