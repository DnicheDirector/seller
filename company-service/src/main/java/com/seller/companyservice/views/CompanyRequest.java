package com.seller.companyservice.views;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CompanyRequest {
  @NotNull
  private String name;
  @NotNull
  private String email;
  @NotNull
  private String description;
}
