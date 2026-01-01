package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BusinessActorDTO {

        @Schema(name = "BusinessActorRequest", description = "Requête de création d'un acteur métier (Propriétaire, Indépendant...)")
        public record Request(
                        @Schema(description = "ID de l'utilisateur Auth", requiredMode = Schema.RequiredMode.REQUIRED, example = "auth0|user-123") UUID authUserId,

                        @Schema(description = "ID de l'organisation (si rattaché)", nullable = true) UUID organizationId,

                        @NotBlank @Schema(example = "John") String firstName,

                        @NotBlank @Schema(example = "Doe") String lastName,

                        String name,

                        @Schema(example = "john.doe@gmail.com") String email,

                        String phoneNumber,
                        String description,

                        @Schema(description = "Est-ce un individu ou une entité ?", defaultValue = "true") Boolean isIndividual,

                        @Schema(defaultValue = "true") Boolean isAvailable,

                        @Schema(description = "Type d'acteur", example = "FREELANCE", allowableValues = {
                                        "FREELANCE", "OWNER", "CONSULTANT" }) String type,

                        @Schema(example = "Architecte") String role,

                        @Schema(description = "Liste des qualifications/compétences", example = "[\"Java\", \"Spring\", \"Architecture\"]") List<String> qualifications,

                        @Schema(description = "Méthodes de paiement acceptées", example = "[\"MOMO\", \"OM\", \"CASH\"]") List<String> paymentMethods,

                        String photoUri,
                        String biography){
        }

        @Schema(name = "BusinessActorResponse", description = "Réponse Business Actor")
        public record Response(
                        UUID id,
                        String code,
                        UUID authUserId,
                        UUID organizationId,
                        String firstName,
                        String lastName,
                        String name,
                        String email,
                        String type,
                        String role,
                        Boolean isIndividual,
                        Boolean isAvailable,
                        Boolean isVerified,
                        Boolean isActive,
                        List<String> qualifications,
                        List<String> paymentMethods,

                        // Relations
                        List<AddressDTO.Response> addresses,
                        List<ContactDTO.Response> contacts,

                        LocalDateTime createdAt) {
        }
}