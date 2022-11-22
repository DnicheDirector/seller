package com.seller.sellersystem.user.views;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
  @NotNull
  private String username;
  @NotNull
  private String email;
  @NotNull
  private String name;
  @NotNull
  private String role;
  @NotNull
  private Long companyId;
}
