package com.seller.usertransactionservice.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaTestContainer extends KafkaContainer {

    private static final String KAFKA_IMAGE = "confluentinc/cp-kafka:latest-ubi8";

    private static KafkaTestContainer kafkaTestContainer;

    private KafkaTestContainer() {
        super(DockerImageName.parse(KAFKA_IMAGE));
    }

    public static KafkaTestContainer getInstance() {
        if (kafkaTestContainer == null) {
            kafkaTestContainer = new KafkaTestContainer();
        }
        return kafkaTestContainer;
    }

    public void addTestContainersProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaTestContainer::getBootstrapServers);
    }

}
