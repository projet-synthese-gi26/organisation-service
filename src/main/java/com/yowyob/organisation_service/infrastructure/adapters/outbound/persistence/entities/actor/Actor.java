package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor;

import lombok.*;
import lombok.experimental.SuperBuilder; // On garde SuperBuilder UNIQUEMENT pour la hiérarchie Actor
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder // Nécessaire ici car Actor a des enfants (Employee, etc.)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Actor {

    @Id
    private UUID id; // ID déplacé ici

    @Column("organization_id")
    private UUID organizationId;

    @Column("auth_user_id")
    private UUID authUserId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String name;

    @Column("phone_number")
    private String phoneNumber;

    private String email;
    private String description;
    private String role;
    private String gender;

    @Column("photo_uri")
    private String photoUri;

    @Column("photo_id")
    private UUID photoId;

    private String nationality;

    @Column("birth_date")
    private LocalDate birthDate;

    private String profession;
    private String biography;
    private UUID[] addresses;
    private UUID[] contacts;

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