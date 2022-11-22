package com.seller.sellersystem.company.views;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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
