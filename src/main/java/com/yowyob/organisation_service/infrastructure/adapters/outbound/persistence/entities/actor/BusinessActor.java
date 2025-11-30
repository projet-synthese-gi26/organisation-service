package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("business_actor")
public class BusinessActor extends Actor {

    private String code;

    @Column("is_individual")
    private Boolean isIndividual;

    @Column("is_available")
    private Boolean isAvailable;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("is_active")
    private Boolean isActive;

    private String type;
    private String role;

    private String[] qualifications;

    @Column("payment_methods")
    private String[] paymentMethods;

    @Column("is_business")
    private Boolean isBusiness;
}