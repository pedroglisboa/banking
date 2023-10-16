package com.plisboa.banking.domain.entity;

import com.plisboa.banking.request.ClientRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Client {
  public Client(ClientRequest clientRequest) {
    this.accountId = clientRequest.getAccountId();
    this.name = clientRequest.getName();
    this.isPrimeExclusive = clientRequest.getIsPrimeExclusive();
    this.balance = clientRequest.getBalance();
    this.birthDate = clientRequest.getBirthDate();
  }

  @Id
  private String accountId;
  private String name;
  private Boolean isPrimeExclusive;
  private BigDecimal balance;
  private LocalDate birthDate;

  public Client() {
    
  }
}
