package com.seller.baseexceptionhandler.handlers;

import com.seller.baseexceptionhandler.views.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class BaseGlobalExceptionHandler {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleNoSuchElementException() {
    return new ErrorMessage("Such element doesn't exists", ZonedDateTime.now());
  }
}
