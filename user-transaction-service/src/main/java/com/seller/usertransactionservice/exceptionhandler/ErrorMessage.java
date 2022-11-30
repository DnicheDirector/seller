package com.seller.usertransactionservice.exceptionhandler;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorMessage {
    private final String description;
    private final ZonedDateTime date;
}
