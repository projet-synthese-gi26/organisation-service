package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ThirdPartyDTO {

    @Schema(description = "Requête de création/maj d'un Tiers (Fournisseur, Partenaire)")
    public record Request(
            @NotNull UUID organizationId,
            @NotBlank String name,
            @Schema(description = "Type de tiers", example = "SUPPLIER", allowableValues = {"SUPPLIER", "PARTNER", "SUBCONTRACTOR"})
            String type,
            
            String legalForm,
            String uniqueIdentificationNumber,
            String tradeRegistrationNumber,
            String taxNumber,
            String logoUri,
            
            @Schema(description = "Comptes comptables associés")
            List<String> accountingAccountNumbers,
            
            List<String> authorizedPaymentMethods,
            BigDecimal authorizedCreditLimit,
            BigDecimal maxDiscountRate,
            Boolean vatSubject,
            Boolean enabled,
            
            @Schema(description = "Données flexibles de classification (JSON)")
            Map<String, Object> classification
    ) {}

    @Schema(description = "Réponse Tiers")
    public record Response(
            UUID id,
            String code,
            UUID organizationId,
            String name,
            String type,
            String taxNumber,
            BigDecimal authorizedCreditLimit,
            Boolean vatSubject,
            Boolean enabled,
            
            // Relations
            List<AddressDTO.Response> addresses,
            List<ContactDTO.Response> contacts,
            
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}