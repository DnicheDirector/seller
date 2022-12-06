package com.seller.sellersystem.position.consumers;

import com.seller.sellersystem.position.exception.UpdatePositionAmountException;
import com.seller.sellersystem.position.producer.UserTransactionProducer;
import com.seller.sellersystem.position.services.PositionService;
import com.seller.sellersystem.position.messages.ReducePositionAmountMessage;
import com.seller.sellersystem.usertransaction.messages.UserTransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionConsumer {

    private final PositionService positionService;
    private final UserTransactionProducer userTransactionProducer;

    @KafkaListener(topics = "${kafka.topics.boughts_topic}")
    public void consumeReducePositionAmountMessage(ReducePositionAmountMessage message) {
        try {
            positionService.subtractPositionAmount(message.getPositionId(), message.getAmount());
            userTransactionProducer.sendUserTransactionStatusMessage(
                    message.getUserTransactionId(), message.getUserId(), UserTransactionStatus.SUCCESS
            );
        } catch (UpdatePositionAmountException e) {
            userTransactionProducer.sendUserTransactionStatusMessage(
                    message.getUserTransactionId(), message.getUserId(), UserTransactionStatus.ERROR
            );
        }

    }
}
