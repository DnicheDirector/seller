package com.seller.usertransactionservice.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoTestContainer extends MongoDBContainer {

    private static final String MONGO_IMAGE = "mongo:6.0.3-focal";
    private static final int PORT = 27017;

    private static MongoTestContainer mongoTestContainer;

    private MongoTestContainer() {
        super(DockerImageName.parse(MONGO_IMAGE));
    }

    public static MongoTestContainer getInstance() {
        if (mongoTestContainer == null) {
            mongoTestContainer = new MongoTestContainer();
            mongoTestContainer.addExposedPort(PORT);
        }
        return mongoTestContainer;
    }

    public void addTestContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoTestContainer::getReplicaSetUrl);
    }
}
