package com.plisboa.banking.utils;

public class BankingConstants {

  private BankingConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String MIN_VALUE_REDIS = "minValue";
  public static final String MAX_VALUE_REDIS = "maxValue";
  public static final String MIN_TAX_REDIS = "minTax";
  public static final String MAX_TAX_REDIS = "maxTax";
  public static final String DEPOSIT_REDIS = "deposito";
  public static final String WITHDRAW_REDIS = "saque";
}
