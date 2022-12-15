package com.seller.sellersystem.position.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReducePositionAmountMessage {
    private String userTransactionId;
    private UUID userId;
    private BigDecimal amount;
    private Long positionId;
}
