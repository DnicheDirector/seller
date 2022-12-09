package com.seller.usertransactionservice.exceptionhandler;

import com.seller.usertransactionservice.usertransaction.exceptions.UserTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserTransactionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage errorMessage(UserTransactionException e) {
    return new ErrorMessage(
            String.format("Exception during user transaction: %s", e.getMessage()),
            ZonedDateTime.now()
    );
  }
}
