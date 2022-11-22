package com.seller.sellersystem.item.views;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;


@Data
@Builder
public class ItemResponse {
  private UUID id;
  private ZonedDateTime created;
  private String name;
  private String description;
  private Long categoryId;
}
