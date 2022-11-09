package com.company.seller.category.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
  @SequenceGenerator(
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
