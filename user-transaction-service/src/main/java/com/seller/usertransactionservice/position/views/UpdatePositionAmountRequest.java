package com.seller.usertransactionservice.position.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdatePositionAmountRequest {
    private BigDecimal amountForSubtract;
}
