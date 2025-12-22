package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.AffiliationDTO;
import com.yowyob.organisation_service.application.services.network.AffiliationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/affiliations")
@RequiredArgsConstructor
@Tag(name = "Gestion des Affiliations", description = "Lier Employés/Acteurs aux Structures")
public class AffiliationController {

    private final AffiliationService affiliationService;

    @PostMapping("/organization")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Affilier à une Organisation", description = "Lie un acteur existant à une organisation.")
    public Mono<ResponseEntity<AffiliationDTO.Response>> linkToOrg(@Valid @RequestBody AffiliationDTO.Request request) {
        return affiliationService.affiliateToOrganization(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @PostMapping("/agency")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Affilier à une Agence", description = "Lie un acteur existant à une agence.")
    public Mono<ResponseEntity<AffiliationDTO.Response>> linkToAgency(@Valid @RequestBody AffiliationDTO.Request request) {
        return affiliationService.affiliateToAgency(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }
}