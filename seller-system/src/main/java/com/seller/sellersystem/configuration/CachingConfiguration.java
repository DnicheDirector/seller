package com.seller.sellersystem.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfiguration {

    @Bean
    public Caffeine<Object, Object> caffeineConfig(CachingProperties cachingProperties) {
        return Caffeine.newBuilder()
                .expireAfterWrite(cachingProperties.getExpiration())
                .maximumSize(cachingProperties.getMaxSize());
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
