package com.seller.sellersystem.user.views;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
  private UUID id;
  private ZonedDateTime created;
  private ZonedDateTime updated;
  private String username;
  private String email;
  private String name;
  private String role;
  private Long companyId;
}
