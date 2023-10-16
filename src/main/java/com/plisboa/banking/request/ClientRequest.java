package com.plisboa.banking.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ClientRequest {

  @NotNull(message = "O campo 'accountId' não pode ser nulo")
  @NotBlank(message = "O 'accountId' não pode estar em branco")
  private String accountId;
  @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
  private String name;
  @NotNull(message = "O campo 'isPrimeExclusive' não pode ser nulo")
  private Boolean isPrimeExclusive;
  @DecimalMin(value = "0.01", message = "O valor mínimo permitido é 0.01")
  @DecimalMax(value = "5000.00", message = "O valor máximo permitido é 5000.00")
  private BigDecimal balance;
  @PastOrPresent(message = "A data de criação deve ser no passado ou presente")
  private LocalDate birthDate;

}
