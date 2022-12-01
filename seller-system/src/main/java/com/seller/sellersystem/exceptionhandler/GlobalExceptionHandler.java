package com.seller.sellersystem.exceptionhandler;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

import com.seller.sellersystem.position.exception.UpdatePositionAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleNoSuchElementException() {
    return new ErrorMessage("Such element doesn't exists", ZonedDateTime.now());
  }

  @ExceptionHandler(UpdatePositionAmountException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleUpdatePositionAmountException(UpdatePositionAmountException e) {
    return new ErrorMessage(
            String.format(
                    "Error while updating position amount: current amount:%s required amount:%s",
                    e.getCurrentAmount(), e.getRequiredAmount()
            ), ZonedDateTime.now()
    );
  }
}
