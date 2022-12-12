package com.seller.usertransactionservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redis.default")
public class RedisProperties {
    private int ttlInMinutes;
}
