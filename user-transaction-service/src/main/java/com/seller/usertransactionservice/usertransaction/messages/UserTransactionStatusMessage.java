package com.seller.usertransactionservice.usertransaction.messages;

import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserTransactionStatusMessage {
    private Long userTransactionId;
    private UserTransactionStatus status;
}
