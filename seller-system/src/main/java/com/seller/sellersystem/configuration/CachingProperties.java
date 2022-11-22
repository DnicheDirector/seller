package com.seller.sellersystem.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cache")
@Data
public class CachingProperties {
    private int expiration;
    private int maxSize;
}
