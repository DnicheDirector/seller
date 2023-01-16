package com.seller.usertransactionservice.usertransaction.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

@ReadingConverter
public enum ZonedDateTimeReadingConverter implements Converter<String, ZonedDateTime> {

    INSTANCE;

    @Override
    public ZonedDateTime convert(@Nullable String source) {
        return source == null ? null : ZonedDateTime.parse(source);
    }
}
