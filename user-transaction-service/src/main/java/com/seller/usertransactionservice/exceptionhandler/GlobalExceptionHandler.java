package com.seller.usertransactionservice.exceptionhandler;

import com.seller.baseexceptionhandler.views.ErrorMessage;
import com.seller.usertransactionservice.usertransaction.exceptions.UserTransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserTransactionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage errorMessage(UserTransactionException e) {
    var errorMessage = new ErrorMessage(
            String.format("Exception during user transaction: %s", e.getMessage()),
            ZonedDateTime.now()
    );
    log.error(errorMessage.getDescription());
    return errorMessage;
  }
}
