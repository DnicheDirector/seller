package com.company.seller.company.views;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
  @NotNull
  private String name;
  @NotNull
  private String email;
  @NotNull
  private String description;
}
