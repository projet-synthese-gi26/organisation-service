package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.Actor;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table("prospect")
public class Prospect extends Actor {

    private String code;

    @Column("transaction_id")
    private UUID transactionId;

    @Column("payment_method")
    private String paymentMethod;

    @Column("amount_paid")
    private BigDecimal amountPaid;

    @Column("interest_level")
    private String interestLevel;
}