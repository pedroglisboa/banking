package com.plisboa.banking.exception;

public class NoBalanceException extends RuntimeException{

  public NoBalanceException(String message) {
    super(message);
  }
}
