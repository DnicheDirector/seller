package com.seller.sellersystem.position.views;

import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PositionRequest {
  @NotNull
  private UUID itemId;
  @NotNull
  private Long companyId;
  @NotNull
  private UUID createdById;
  @NotNull
  @DecimalMin(value = "0.01")
  private BigDecimal amount;
}
