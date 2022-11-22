package com.seller.sellersystem.position.models;

import com.seller.sellersystem.item.models.Item;
import com.seller.sellersystem.user.models.User;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_id_generator")
  @SequenceGenerator(
          name = "position_id_generator",
          sequenceName = "position_sequence",
          allocationSize = 1
  )
  private Long id;

  @ManyToOne(optional = false)
  private Item item;

  @NotNull
  private Long companyId;

  @ManyToOne(optional = false)
  private User createdBy;

  @NotNull
  @Column(updatable = false)
  private ZonedDateTime created;

  @NotNull
  @DecimalMin(value = "0.01")
  private BigDecimal amount;
}
