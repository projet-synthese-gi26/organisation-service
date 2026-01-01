package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class ContactDTO {

        @Schema(name = "ContactRequest", description = "Requête de création de contact")
        public record Request(
                        @NotNull(message = "L'ID de l'entité parente est obligatoire") @Schema(description = "ID de l'organisation, agence ou acteur", example = "123e4567-e89b-12d3-a456-426614174000") UUID contactableId,

                        @NotBlank(message = "Le type d'entité est obligatoire") @Schema(description = "Type de l'entité parente", example = "ORGANIZATION", allowableValues = {
                                        "ORGANIZATION", "AGENCY", "ACTOR" }) String contactableType,

                        @Schema(description = "Titre ou civilité", example = "Mr") String title,

                        @NotBlank(message = "Prénom") @Schema(example = "Jean") String firstName,

                        @NotBlank(message = "Nom") @Schema(example = "Dupont") String lastName,

                        @Email @NotBlank(message = "Email obligatoire") @Schema(example = "jean.dupont@example.com") String email,

                        @Schema(description = "Numéro de téléphone principal", example = "+237 699 99 99 99") String phoneNumber,

                        String secondaryPhoneNumber,
                        String faxNumber,
                        String secondaryEmail,

                        @Schema(description = "Définir comme contact favori/principal", defaultValue = "false") Boolean isFavorite){
        }

        @Schema(name = "ContactResponse", description = "Réponse contact")
        public record Response(
                        UUID id,
                        UUID contactableId,
                        String contactableType,
                        String firstName,
                        String lastName,
                        String title,
                        String email,
                        String phoneNumber,
                        String secondaryPhoneNumber,
                        Boolean isFavorite,
                        Boolean isEmailVerified,
                        Boolean isPhoneNumberVerified,
                        LocalDateTime createdAt) {
        }
}