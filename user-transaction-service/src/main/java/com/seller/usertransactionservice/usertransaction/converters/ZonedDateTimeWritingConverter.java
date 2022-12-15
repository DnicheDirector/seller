package com.seller.usertransactionservice.usertransaction.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

@WritingConverter
public enum ZonedDateTimeWritingConverter implements Converter<ZonedDateTime, String> {

    INSTANCE;

    @Override
    public String convert(@Nullable ZonedDateTime source) {
        return source == null ? null : source.toString();
    }
}
