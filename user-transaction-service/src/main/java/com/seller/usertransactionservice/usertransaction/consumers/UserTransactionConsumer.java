package com.seller.usertransactionservice.usertransaction.consumers;

import com.seller.usertransactionservice.usertransaction.messages.UserTransactionStatusMessage;
import com.seller.usertransactionservice.usertransaction.services.UserTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTransactionConsumer {

    private final UserTransactionService userTransactionService;

    @KafkaListener(topics = "${kafka.topics.transaction_status_topic}")
    public void consumeUserTransactionStatusMessage(UserTransactionStatusMessage message) {
        if (message.getStatus() != null) {
            log.info("Receiving update transaction status message: {}", message);
            userTransactionService.updateUserTransactionStatus(message.getUserTransactionId(), message.getStatus())
                    .subscribe(result ->
                               log.info("User transaction status was successfully updated: {}", message),
                            error ->
                               log.error("Error during transaction status update: {}. {}", error.getMessage(), message)
                    );
        }
    }
}
