package com.seller.usertransactionservice.exceptionhandler;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleNoSuchElementException() {
    return new ErrorMessage("Such element doesn't exists", ZonedDateTime.now());
  }

  @ExceptionHandler(FeignException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleUserTransactionException(FeignException e) {
    return e.contentUTF8();
  }
}
