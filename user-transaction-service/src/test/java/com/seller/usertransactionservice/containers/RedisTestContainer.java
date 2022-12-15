package com.seller.usertransactionservice.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RedisTestContainer extends GenericContainer<RedisTestContainer> {

    private static final String REDIS_IMAGE = "redis:7.0.5-bullseye";
    private static final int PORT = 6379;

    private static RedisTestContainer redisTestContainer;

    private RedisTestContainer() {
       super(DockerImageName.parse(REDIS_IMAGE));
    }

    public static RedisTestContainer getInstance() {
        if (redisTestContainer == null) {
            redisTestContainer = new RedisTestContainer().withExposedPorts(PORT);
        }
        return redisTestContainer;
    }

    public void addTestContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redisTestContainer::getHost);
        registry.add("spring.redis.port", () -> redisTestContainer.getMappedPort(PORT));
    }
}
