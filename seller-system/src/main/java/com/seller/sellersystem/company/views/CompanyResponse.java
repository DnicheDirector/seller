package com.seller.sellersystem.company.views;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;


@Data
@Builder
public class CompanyResponse {
  private Long id;
  private ZonedDateTime created;
  private String name;
  private String email;
  private String description;
}
