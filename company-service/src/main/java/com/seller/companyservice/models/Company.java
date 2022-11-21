package com.seller.companyservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@Table(schema = Company.SCHEMA, name = Company.TABLE)
@NoArgsConstructor
@AllArgsConstructor
public class Company {

  public static final String SCHEMA = "companies";
  public static final String TABLE = "company";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_generator")
  @SequenceGenerator(
          schema = SCHEMA,
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
