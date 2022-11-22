package com.seller.sellersystem.item.views;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRequest {
  @NotNull
  private String name;
  private String description;
  @NotNull
  private Long categoryId;
}
