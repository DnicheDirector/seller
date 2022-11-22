package com.seller.sellersystem.category.views;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryRequest {
  @NotNull
  private String name;
  private Long parentCategoryId;
  private String description;
}
