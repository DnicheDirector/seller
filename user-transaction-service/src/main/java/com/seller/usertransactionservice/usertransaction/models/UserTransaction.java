package com.seller.usertransactionservice.usertransaction.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_transaction_id_generator")
    @SequenceGenerator(
            name = "user_transaction_id_generator",
            sequenceName = "user_transaction_sequence",
            allocationSize = 1
    )
    private Long id;

    @NotNull
    private Long positionId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    @Column(updatable = false)
    private ZonedDateTime created;

    @NotNull
    private UUID createdById;
}
