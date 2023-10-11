package com.plisboa.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NoBalanceBadRequestException.class)
  public ResponseEntity<Object> handleCustomBadRequestException(
      NoBalanceBadRequestException ex, WebRequest request) {
    String errorMessage = ex.getMessage();
    return handleExceptionInternal(ex, errorMessage, null, HttpStatus.BAD_REQUEST, request);
  }

}

