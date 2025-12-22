package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty;

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
@Table("third_party")
public class ThirdParty {

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
    private String code;

    @Column("organization_id")
    private UUID organizationId;

    private String type;

    @Column("legal_form")
    private String legalForm;

    @Column("unique_identification_number")
    private String uniqueIdentificationNumber;

    @Column("trade_registration_number")
    private String tradeRegistrationNumber;

    private String name;
    private String acronym;

    @Column("long_name")
    private String longName;

    @Column("logo_uri")
    private String logoUri;

    @Column("logo_id")
    private UUID logoId;

    @Column("accounting_account_numbers")
    private String[] accountingAccountNumbers;

    @Column("authorized_payment_methods")
    private String[] authorizedPaymentMethods;

    @Column("authorized_credit_limit")
    private BigDecimal authorizedCreditLimit;

    @Column("max_discount_rate")
    private BigDecimal maxDiscountRate;

    @Column("vat_subject")
    private Boolean vatSubject;

    @Column("operations_balance")
    private BigDecimal operationsBalance;

    @Column("opening_balance")
    private BigDecimal openingBalance;

    @Column("pay_term_number")
    private Integer payTermNumber;

    @Column("pay_term_type")
    private String payTermType;

    @Column("third_party_family")
    private String thirdPartyFamily;

    private String classification; // Stocké en String (JSON) ou JsonObject selon driver

    @Column("tax_number")
    private String taxNumber;

    @Column("loyalty_points")
    private Integer loyaltyPoints;

    @Column("loyalty_points_used")
    private Integer loyaltyPointsUsed;

    @Column("loyalty_points_expired")
    private Integer loyaltyPointsExpired;

    private Boolean enabled;
}