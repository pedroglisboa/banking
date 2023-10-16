package com.plisboa.banking.utils;

import java.math.BigDecimal;

public class BankingConstants {

  private BankingConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final BigDecimal MIN_VALUE = new BigDecimal("100");
  public static final BigDecimal MAX_VALUE = new BigDecimal("300");
  public static final BigDecimal MIN_TAX = new BigDecimal("0.004");
  public static final BigDecimal MAX_TAX = new BigDecimal("0.01");
  public static final String DEPOSIT_STRING = "deposito";
  public static final String WITHDRAW_STRING = "saque";
}
