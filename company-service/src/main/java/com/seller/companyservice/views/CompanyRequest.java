package com.seller.companyservice.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

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
