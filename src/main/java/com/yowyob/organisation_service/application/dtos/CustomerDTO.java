package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CustomerDTO {

        @Schema(name = "CustomerRequest", description = "Requête création Client")
        public record Request(
                        @NotNull @Schema(description = "ID de l'organisation propriétaire du client") UUID organizationId,

                        @NotBlank @Schema(example = "Paul") String firstName,

                        @NotBlank @Schema(example = "Biya") String lastName,

                        @Schema(example = "paul@client.com") String email,

                        @Schema(example = "+237 600 00 00 00") String phoneNumber,

                        @Schema(example = "MOMO", allowableValues = {
                                        "MOMO", "CASH", "CARD" }) String paymentMethod,

                        @Schema(defaultValue = "true") Boolean isIndividual,

                        @Schema(defaultValue = "false") Boolean isVerified){
        }

        @Schema(name = "CustomerResponse", description = "Réponse Client")
        public record Response(
                        UUID id,
                        String code,
                        UUID organizationId,
                        String firstName,
                        String lastName,
                        String email,
                        String phoneNumber,
                        BigDecimal amountPaid,
                        Boolean isVerified,

                        // Relations
                        List<AddressDTO.Response> addresses,
                        List<ContactDTO.Response> contacts,

                        LocalDateTime createdAt) {
        }
}