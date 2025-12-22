package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("proposed_activity")
public class ProposedActivity {

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
    @Column("organization_id")
    private UUID organizationId;

    private String type;
    private String name;
    private BigDecimal rate;
    private String description;
}