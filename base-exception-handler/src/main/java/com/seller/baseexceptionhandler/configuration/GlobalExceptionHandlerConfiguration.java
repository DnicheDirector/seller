package com.seller.baseexceptionhandler.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "base-exception-handler", name = "enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan({"com.seller.baseexceptionhandler"})
public class GlobalExceptionHandlerConfiguration {
}
