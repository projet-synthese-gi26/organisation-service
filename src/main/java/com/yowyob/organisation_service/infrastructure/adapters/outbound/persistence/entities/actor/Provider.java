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
@Table("provider")
public class Provider extends Actor {

    private String code;

    @Column("contact_info")
    private String contactInfo;

    private String address;

    @Column("is_active")
    private Boolean isActive;

    @Column("product_service_type")
    private String productServiceType;
}