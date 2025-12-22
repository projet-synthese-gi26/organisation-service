package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table("sales_person")
public class SalesPerson extends Actor {

    private String code;

    @Column("commission_rate")
    private Double commissionRate;

    @Column("current_balance")
    private Double currentBalance;

    private Double credit;
}