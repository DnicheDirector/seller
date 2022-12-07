package com.seller.usertransactionservice.usertransaction.kafka;

import com.seller.usertransactionservice.usertransaction.messages.ReducePositionAmountMessage;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Getter
public class KafkaTestConsumer {

    private ReducePositionAmountMessage consumedMessage;
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "${kafka.topics.boughts_topic}")
    public void consumeReducePositionAmountMessage(ReducePositionAmountMessage message) {
        this.consumedMessage = message;
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
