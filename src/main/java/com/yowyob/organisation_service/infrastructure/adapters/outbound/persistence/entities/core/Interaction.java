package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core;

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
@Table("interaction")
public class Interaction {

    @Id
    private UUID id;

    // --- Champs d'audit ---
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

    // --- Champs Métiers ---
    @Column("interaction_id")
    private UUID interactionId; // Note: Si c'est un ID métier distinct de la PK

    @Column("prospect_id")
    private UUID prospectId;

    @Column("interaction_date")
    private LocalDateTime interactionDate;

    private String notes;
}