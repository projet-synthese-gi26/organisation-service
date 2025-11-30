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
@Table("employee")
public class Employee extends Actor {

    private String code;

    @Column("is_manager")
    private Boolean isManager;

    private String role;

    private String department;
}