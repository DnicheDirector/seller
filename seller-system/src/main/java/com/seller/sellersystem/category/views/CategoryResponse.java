package com.seller.sellersystem.category.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponse {
  private Long id;
  private String name;
  private Long parentCategoryId;
  private String description;
}
