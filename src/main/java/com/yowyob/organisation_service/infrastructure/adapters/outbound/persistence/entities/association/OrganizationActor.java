package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.association;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("organization_actor")
public class OrganizationActor extends BaseEntity {

    @Column("organization_id")
    private UUID organizationId;

    @Column("actor_id")
    private UUID actorId;

    private String type;

    @Column("is_active")
    private Boolean isActive;
}