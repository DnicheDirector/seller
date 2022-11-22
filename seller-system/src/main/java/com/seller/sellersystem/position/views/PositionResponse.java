package com.seller.sellersystem.position.views;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionResponse {
  private Long id;
  private ZonedDateTime created;
  private UUID itemId;
  private Long companyId;
  private UUID createdById;
  private BigDecimal amount;
}
