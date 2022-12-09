package com.seller.sellersystem.position.controllers.kafka;

import com.seller.sellersystem.usertransaction.messages.UserTransactionStatusMessage;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@Getter
public class KafkaTestConsumer {

    private final List<UserTransactionStatusMessage> consumedMessages = new ArrayList<>();
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "${kafka.topics.transaction_status_topic}")
    public void consumeUserTransactionStatusMessage(UserTransactionStatusMessage message) {
        consumedMessages.add(message);
        latch.countDown();
    }

    public void setLatch(int count) {
        latch = new CountDownLatch(count);
    }

    public void reset() {
        consumedMessages.clear();
        latch = new CountDownLatch(1);
    }
}
