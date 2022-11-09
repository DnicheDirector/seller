package com.company.seller.company.views;

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
public class CompanyInputViewModel {
  @NotNull
  private String name;
  @NotNull
  private String email;
  @NotNull
  private LocalDateTime created;
  @NotNull
  private String description;
}
