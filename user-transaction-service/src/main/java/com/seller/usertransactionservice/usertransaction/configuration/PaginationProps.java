package com.seller.usertransactionservice.usertransaction.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "pagination")
public class PaginationProps {
    private Integer maxPageSize;
}
