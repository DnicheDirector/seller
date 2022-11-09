package com.company.seller.item.models;

import com.company.seller.category.models.Category;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @NotNull
  private String name;

  private String description;

  @NotNull
  private LocalDateTime created;

  @ManyToOne(optional = false)
  private Category category;

}
