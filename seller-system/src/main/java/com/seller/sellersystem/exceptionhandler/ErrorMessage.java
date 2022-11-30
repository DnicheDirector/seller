package com.seller.sellersystem.exceptionhandler;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorMessage {
    private final String description;
    private final ZonedDateTime date;
}
