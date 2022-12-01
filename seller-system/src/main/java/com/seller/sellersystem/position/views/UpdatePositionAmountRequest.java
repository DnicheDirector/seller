package com.seller.sellersystem.position.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdatePositionAmountRequest {
    @DecimalMin(value = "0.01")
    private BigDecimal amountForSubtract;
}
