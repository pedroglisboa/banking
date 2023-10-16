package com.plisboa.banking.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRequest {

  @DecimalMin(value = "0.01", message = "O valor mínimo permitido é 0.01")
  @DecimalMax(value = "5000.00", message = "O valor máximo permitido é 5000.00")
  private BigDecimal value;
}
