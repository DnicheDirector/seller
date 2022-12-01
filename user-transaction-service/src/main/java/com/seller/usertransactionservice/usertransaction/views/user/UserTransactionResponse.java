package com.seller.usertransactionservice.usertransaction.views.user;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class UserTransactionResponse {
    private Long id;
    private Long positionId;
    private BigDecimal amount;
    private ZonedDateTime created;
    private UUID createdById;
}
