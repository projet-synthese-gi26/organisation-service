package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CustomerDTO {

    @Schema(description = "Requête création Client")
    public record Request(
            @NotNull UUID organizationId,
            @NotBlank String firstName,
            @NotBlank String lastName,
            String email,
            String phoneNumber,
            String paymentMethod,
            Boolean isIndividual,
            Boolean isVerified
    ) {}

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
            
            LocalDateTime createdAt
    ) {}
}