package com.seller.sellersystem.category.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = Category.SCHEMA, name = Category.TABLE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  public static final String SCHEMA = "seller_system";
  public static final String TABLE = "category";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
  @SequenceGenerator(
          schema = SCHEMA,
          name = "category_id_generator",
          sequenceName = "category_sequence",
          allocationSize = 1
  )
  private Long id;

  @NotNull
  private String name;

  @ManyToOne
  private Category parentCategory;

  private String description;

}
