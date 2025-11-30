package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Actor extends BaseEntity {

    @Column("organization_id")
    private UUID organizationId;

    @Column("auth_user_id")
    private UUID authUserId; // Lien avec l'API d'autnetification

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
}