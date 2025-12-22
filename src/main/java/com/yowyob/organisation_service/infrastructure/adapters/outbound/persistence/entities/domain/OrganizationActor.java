package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("organization_actor")
public class OrganizationActor {

    @Id
    private UUID id;

    @Column("organization_id")
    private UUID organizationId;

    @Column("actor_id")
    private UUID actorId;

    private String type;

    @Column("is_active")
    private Boolean isActive;

    // --- Champs de l'ex-BaseEntity ---
    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    @Version
    private Long version;
}