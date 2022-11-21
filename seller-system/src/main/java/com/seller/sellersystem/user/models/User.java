package com.seller.sellersystem.user.models;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = User.SCHEMA, name = User.TABLE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  public static final String SCHEMA = "seller_system";
  public static final String TABLE = "users";

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
  @Type(type = "role_enum")
  private Role role;

  @NotNull
  @Column(updatable = false)
  private ZonedDateTime created;

  @NotNull
  private ZonedDateTime updated;

  @NotNull
  private Long companyId;
}
