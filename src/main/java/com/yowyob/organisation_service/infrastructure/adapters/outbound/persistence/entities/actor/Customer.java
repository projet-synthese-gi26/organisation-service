package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table("customer")
public class Customer extends Actor {

    private String code;

    @Column("is_individual")
    private Boolean isIndividual;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("is_active")
    private Boolean isActive;

    @Column("payment_method")
    private String paymentMethod;

    @Column("amount_paid")
    private BigDecimal amountPaid;
}