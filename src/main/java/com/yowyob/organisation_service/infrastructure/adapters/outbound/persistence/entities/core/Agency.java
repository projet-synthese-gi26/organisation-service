package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("agency")
public class Agency extends BaseEntity {

    private String code;

    @Column("organization_id")
    private UUID organizationId;

    @Column("owner_id")
    private UUID ownerId;

    @Column("manager_id")
    private UUID managerId;

    private String name;
    private String location;
    private String description;

    @Column("transferable")
    private Boolean transferable;

    @Column("is_active")
    private Boolean isActive;

    @Column("logo_uri")
    private String logoUri;

    @Column("logo_id")
    private UUID logoId;

    @Column("short_name")
    private String shortName;

    @Column("long_name")
    private String longName;

    @Column("is_individual_business")
    private Boolean isIndividualBusiness;

    @Column("is_headquarter")
    private Boolean isHeadquarter;

    private String country;
    private String city;
    private Double latitude;
    private Double longitude;

    @Column("open_time")
    private String openTime;

    @Column("close_time")
    private String closeTime;

    private String phone;
    private String email;
    private String whatsapp;

    @Column("greeting_message")
    private String greetingMessage;

    @Column("average_revenue")
    private BigDecimal averageRevenue;

    @Column("capital_share")
    private BigDecimal capitalShare;

    @Column("social_network")
    private String socialNetwork;

    @Column("tax_number")
    private String taxNumber;

    @Column("registration_number")
    private String registrationNumber;

    private String[] keywords;

    @Column("is_public")
    private Boolean isPublic;

    @Column("is_business")
    private Boolean isBusiness;

    @Column("total_affiliated_customers")
    private Integer totalAffiliatedCustomers;
}