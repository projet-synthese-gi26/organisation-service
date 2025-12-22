package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.AgencyDTO;
import com.yowyob.organisation_service.application.services.network.AgencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agencies")
@RequiredArgsConstructor
@Tag(name = "Gestion des Agences", description = "Structure du réseau")
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une agence", description = "Crée une agence rattachée à une organisation.")
    public Mono<ResponseEntity<AgencyDTO.Response>> create(@Valid @RequestBody AgencyDTO.Request request) {
        return agencyService.createAgency(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AgencyDTO.Response>> getById(@PathVariable UUID id) {
        return agencyService.getAgencyById(id).map(ResponseEntity::ok);
    }

    @GetMapping("/organization/{orgId}")
    @Operation(summary = "Lister les agences d'une organisation")
    public Flux<AgencyDTO.Response> getByOrganization(@PathVariable UUID orgId) {
        return agencyService.getAgenciesByOrganization(orgId);
    }
}