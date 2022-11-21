package com.seller.sellersystem.item.models;

import com.seller.sellersystem.category.models.Category;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = Item.SCHEMA, name = Item.TABLE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  public static final String SCHEMA = "seller_system";
  public static final String TABLE = "item";

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @NotNull
  private String name;

  private String description;

  @NotNull
  @Column(updatable = false)
  private ZonedDateTime created;

  @ManyToOne(optional = false)
  private Category category;

}
