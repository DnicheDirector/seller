package com.seller.companyservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_generator")
  @SequenceGenerator(
          name = "company_id_generator",
          sequenceName = "company_sequence",
          allocationSize = 1
  )
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  @Column(updatable = false)
  private ZonedDateTime created;

  @NotNull
  private String description;
}
