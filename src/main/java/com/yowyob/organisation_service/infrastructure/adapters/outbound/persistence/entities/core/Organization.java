package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core;

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
@Table("organization")
public class Organization {

    @Id
    private UUID id;

    // --- Champs d'audit (Ex-BaseEntity) ---
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
    private String code;
    private String service;

    @Column("business_actor_id")
    private UUID businessActorId;

    @Column("is_individual_business")
    private Boolean isIndividualBusiness;

    private String email;

    @Column("short_name")
    private String shortName;

    @Column("long_name")
    private String longName;

    private String description;

    @Column("logo_uri")
    private String logoUri;

    @Column("logo_id")
    private UUID logoId;

    @Column("website_url")
    private String websiteUrl;

    @Column("social_network")
    private String socialNetwork;

    @Column("business_registration_number")
    private String businessRegistrationNumber;

    @Column("tax_number")
    private String taxNumber;

    @Column("capital_share")
    private BigDecimal capitalShare;

    @Column("ceo_name")
    private String ceoName;

    @Column("year_founded")
    private Integer yearFounded;

    private String[] keywords;

    @Column("number_of_employees")
    private Integer numberOfEmployees;

    @Column("legal_form")
    private String legalForm;

    @Column("is_active")
    private Boolean isActive;

    private String status;
}