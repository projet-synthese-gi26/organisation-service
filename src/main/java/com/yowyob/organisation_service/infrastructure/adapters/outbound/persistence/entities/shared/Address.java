package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared;

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
@Table("address")
public class Address {

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
    @Column("addressable_id")
    private UUID addressableId;

    @Column("addressable_type")
    private String addressableType; // "ORGANIZATION", "AGENCY"

    private String type;

    @Column("address_line_1")
    private String addressLine1;

    @Column("address_line_2")
    private String addressLine2;

    private String city;
    private String state;
    private String locality;

    @Column("zip_code")
    private String zipCode;

    @Column("country_id")
    private UUID countryId;

    @Column("po_box")
    private String poBox;

    @Column("neighbor_hood")
    private String neighborHood; // Quartier

    @Column("informal_description")
    private String informalDescription;

    @Column("is_default")
    private Boolean isDefault;

    private Double latitude;
    private Double longitude;
}