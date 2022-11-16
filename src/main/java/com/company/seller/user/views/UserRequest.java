package com.company.seller.user.views;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
