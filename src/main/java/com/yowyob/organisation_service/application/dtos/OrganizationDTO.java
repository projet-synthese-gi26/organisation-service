package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrganizationDTO {

    @Schema(name = "OrganizationRequest", description = "Requête de création/modification d'une organisation")
    public record Request(
            @NotNull(message = "L'ID du propriétaire (Business Actor) est obligatoire")
            @Schema(description = "ID de l'utilisateur propriétaire", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID businessActorId,

            @NotBlank(message = "Le nom court (Sigle) est obligatoire")
            @Size(max = 20, message = "Le nom court ne doit pas dépasser 20 caractères")
            @Schema(description = "Sigle ou nom court", example = "YOW")
            String shortName,

            @NotBlank(message = "Le nom complet est obligatoire")
            @Schema(description = "Raison sociale complète", example = "YowYob Technology Inc.")
            String longName,

            @NotBlank(message = "Le secteur d'activité est obligatoire")
            @Schema(description = "Secteur d'activité", example = "IT_SERVICES")
            String service,

            @Email(message = "L'email doit être valide")
            @NotBlank(message = "L'email est obligatoire")
            @Schema(description = "Email de contact officiel", example = "contact@yowyob.com")
            String email,

            @Schema(description = "Description de l'organisation", example = "Leader technologique en Afrique")
            String description,

            @Schema(description = "URL du logo", example = "https://cdn.yowyob.com/logo.png")
            String logoUri,

            @Schema(description = "ID du fichier logo (si stocké en interne)")
            UUID logoId,

            @Schema(description = "Site web officiel", example = "https://www.yowyob.com")
            String websiteUrl,

            @Schema(description = "Liens réseaux sociaux (JSON ou String)", example = "{\"facebook\": \"fb.com/yowyob\", \"linkedin\": \"in/yowyob\"}")
            String socialNetwork,

            @Schema(description = "Numéro d'enregistrement (RCCM/SIRET)", example = "RC-DLA-2024-B-1234")
            String businessRegistrationNumber,

            @Schema(description = "Numéro contribuable (Tax ID)", example = "M123456789")
            String taxNumber,

            @DecimalMin(value = "0.0", message = "Le capital doit être positif")
            @Schema(description = "Capital social", example = "1000000.00")
            BigDecimal capitalShare,

            @Schema(description = "Nom du Directeur Général", example = "Jean Dupont")
            String ceoName,

            @Min(value = 1900, message = "L'année de fondation doit être valide")
            @Schema(description = "Année de création", example = "2024")
            Integer yearFounded,

            @Schema(description = "Nombre d'employés estimé", example = "50")
            Integer numberOfEmployees,

            @Schema(description = "Forme juridique", example = "SARL")
            String legalForm,

            @Schema(description = "Est-ce une entreprise individuelle ?", defaultValue = "false")
            Boolean isIndividualBusiness,

            @Schema(description = "Liste de mots-clés", example = "[\"Tech\", \"Innovation\", \"Cloud\"]")
            List<String> keywords
    ) {}

    @Schema(name = "OrganizationResponse", description = "Réponse contenant les détails de l'organisation")
    public record Response(
            UUID id,
            String code,
            String service,
            UUID businessActorId,
            Boolean isIndividualBusiness,
            String email,
            String shortName,
            String longName,
            String description,
            String logoUri,
            UUID logoId,
            String websiteUrl,
            String socialNetwork,
            String businessRegistrationNumber,
            String taxNumber,
            BigDecimal capitalShare,
            String ceoName,
            Integer yearFounded,
            
            @Schema(description = "Liste des adresses associées")
            List<AddressDTO.Response> addresses,

            @Schema(description = "Liste des contacts associés")
            List<ContactDTO.Response> contacts,

            @Schema(description = "Liste des mots-clés")
            List<String> keywords,
            
            Integer numberOfEmployees,
            String legalForm,
            Boolean isActive,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}