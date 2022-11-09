package com.company.seller.position.views;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class PositionInputViewModel {
  @NotNull
  private UUID itemId;
  @NotNull
  private Long companyId;
  @NotNull
  private UUID createdById;
  @NotNull
  private LocalDateTime created;
  @NotNull
  @DecimalMin(value = "0.01")
  private BigDecimal amount;
}
