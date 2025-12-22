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
@Table("business_domain")
public class BusinessDomain {

    @Id
    private UUID id;

    private String code;
    private String service;

    @Column("parent_id")
    private UUID parentId;

    private String name;

    @Column("image_uri")
    private String imageUri;

    @Column("image_id")
    private UUID imageId;

    private String type;

    @Column("type_label")
    private String typeLabel;

    private String description;

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