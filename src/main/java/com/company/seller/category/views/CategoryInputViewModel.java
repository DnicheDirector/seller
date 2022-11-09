package com.company.seller.category.views;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInputViewModel {
  @NotNull
  private String name;
  private Long parentCategoryId;
  private String description;
}
