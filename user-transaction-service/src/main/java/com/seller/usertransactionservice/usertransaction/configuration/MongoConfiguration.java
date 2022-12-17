package com.seller.usertransactionservice.usertransaction.configuration;

import com.seller.usertransactionservice.usertransaction.converters.ZonedDateTimeReadingConverter;
import com.seller.usertransactionservice.usertransaction.converters.ZonedDateTimeWritingConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions customConversions() {
        var converters = new ArrayList<>();
        converters.add(ZonedDateTimeReadingConverter.INSTANCE);
        converters.add(ZonedDateTimeWritingConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }

}
