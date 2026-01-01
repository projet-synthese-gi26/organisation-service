package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProspectDTO {

        @Schema(name = "ProspectRequest", description = "Requête création Prospect")
        public record Request(
                        @NotNull UUID organizationId,

                        @Schema(example = "Sarah") String firstName,

                        @Schema(example = "Connor") String lastName,

                        @Schema(example = "sarah@future.com") String email,

                        String phoneNumber,

                        @Schema(description = "Niveau d'intérêt", example = "HOT", allowableValues = {
                                        "COLD", "WARM", "HOT" }) String interestLevel){
        }

        @Schema(name = "ProspectResponse", description = "Réponse Prospect avec historique")
        public record Response(
                        UUID id,
                        String code,
                        UUID organizationId,
                        String firstName,
                        String lastName,
                        String interestLevel,

                        // Inclusion de l'historique
                        @Schema(description = "Historique des interactions") List<InteractionDTO.Response> interactions,

                        // Relations standards
                        List<AddressDTO.Response> addresses,
                        List<ContactDTO.Response> contacts,

                        LocalDateTime createdAt) {
        }
}