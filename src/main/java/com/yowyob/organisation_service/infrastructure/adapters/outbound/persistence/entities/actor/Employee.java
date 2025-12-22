package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("employee")
public class Employee extends Actor {

    private String code;

    @Column("is_manager")
    private Boolean isManager;

    private String role;

    private String department;
}