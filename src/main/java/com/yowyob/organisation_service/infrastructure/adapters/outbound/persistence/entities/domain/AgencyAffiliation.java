package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder // Builder standard (plus simple)
@NoArgsConstructor
@AllArgsConstructor
@Table("agency_affiliation")
public class AgencyAffiliation {

    @Id
    private UUID id;

    @Column("organization_id")
    private UUID organizationId;

    @Column("agency_id")
    private UUID agencyId;

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