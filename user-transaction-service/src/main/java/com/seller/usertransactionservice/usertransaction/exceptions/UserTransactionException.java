package com.seller.usertransactionservice.usertransaction.exceptions;

import java.math.BigDecimal;

public class UserTransactionException extends RuntimeException {
    public UserTransactionException(String message) {
        super(message);
    }

    public UserTransactionException(BigDecimal currentAmount, BigDecimal requiredAmount) {
        super(String.format("current amount: %s, required amount: %s", currentAmount, requiredAmount));
    }
}
