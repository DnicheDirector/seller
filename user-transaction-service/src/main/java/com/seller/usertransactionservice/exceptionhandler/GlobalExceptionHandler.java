package com.seller.usertransactionservice.exceptionhandler;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(FeignException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleUserTransactionException(FeignException e) {
    return e.contentUTF8();
  }
}
