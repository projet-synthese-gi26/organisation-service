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
@Table("contact")
public class Contact {

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
    @Column("contactable_id")
    private UUID contactableId;

    @Column("contactable_type")
    private String contactableType; // "ORGANIZATION", "AGENCY", "ACTOR"...

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String title;

    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("secondary_phone_number")
    private String secondaryPhoneNumber;

    @Column("fax_number")
    private String faxNumber;

    @Column("secondary_email")
    private String secondaryEmail;

    @Column("is_email_verified")
    private Boolean isEmailVerified;

    @Column("is_phone_number_verified")
    private Boolean isPhoneNumberVerified;

    @Column("is_favorite")
    private Boolean isFavorite;

    @Column("email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column("phone_verified_at")
    private LocalDateTime phoneVerifiedAt;
}