package com.seller.sellersystem.item.views;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
  @NotNull
  private String name;
  private String description;
  @NotNull
  private Long categoryId;
}
