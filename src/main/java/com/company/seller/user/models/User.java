package com.company.seller.user.models;

import com.company.seller.company.models.Company;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @NotNull
  private String username;

  @NotNull
  private String email;

  @NotNull
  private String name;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Role role;

  @NotNull
  private LocalDateTime created;

  @NotNull
  private LocalDateTime updated;

  @ManyToOne(optional = false)
  private Company company;
}
