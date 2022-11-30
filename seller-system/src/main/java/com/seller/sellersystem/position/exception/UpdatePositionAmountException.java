package com.seller.sellersystem.position.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class UpdatePositionAmountException extends RuntimeException {
    private final BigDecimal currentAmount;
    private final BigDecimal requiredAmount;
}
