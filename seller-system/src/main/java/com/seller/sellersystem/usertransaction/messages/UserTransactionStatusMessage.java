package com.seller.sellersystem.usertransaction.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserTransactionStatusMessage {
    private String userTransactionId;
    private UserTransactionStatus status;
}
