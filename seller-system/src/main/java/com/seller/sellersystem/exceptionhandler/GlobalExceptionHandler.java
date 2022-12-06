package com.seller.sellersystem.exceptionhandler;

import java.time.ZonedDateTime;
import com.seller.baseexceptionhandler.views.ErrorMessage;
import com.seller.sellersystem.position.exception.UpdatePositionAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
