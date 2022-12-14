package com.seller.usertransactionservice.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "redis.default")
public class RedisProperties {
    @DurationMin
    private Duration expiration;
}
