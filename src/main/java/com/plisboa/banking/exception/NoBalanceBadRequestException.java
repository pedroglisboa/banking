package com.plisboa.banking.exception;

public class NoBalanceBadRequestException extends RuntimeException {
  public NoBalanceBadRequestException(String message) {
    super(message);
  }
}