package com.seller.usertransactionservice.position.views;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

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
