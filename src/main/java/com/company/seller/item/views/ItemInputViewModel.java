package com.company.seller.item.views;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInputViewModel {
  @NotNull
  private String name;
  private String description;
  @NotNull
  private LocalDateTime created;
  @NotNull
  private Long categoryId;
}
