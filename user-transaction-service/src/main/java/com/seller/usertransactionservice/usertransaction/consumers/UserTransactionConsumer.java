package com.seller.usertransactionservice.usertransaction.consumers;

import com.seller.usertransactionservice.usertransaction.messages.UserTransactionStatusMessage;
import com.seller.usertransactionservice.usertransaction.services.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTransactionConsumer {

    private final UserTransactionService userTransactionService;

    @KafkaListener(topics = "${kafka.topics.transaction_status_topic}")
    public void consumeUserTransactionStatusMessage(UserTransactionStatusMessage message) {
        if (message.getStatus() != null) {
            userTransactionService.updateUserTransactionStatus(message.getUserTransactionId(), message.getStatus())
                    .subscribe();
        }
    }
}
