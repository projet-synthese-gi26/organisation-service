package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.ThirdPartyDTO;
import com.yowyob.organisation_service.application.services.partnership.ThirdPartyService;
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
@RequestMapping("/api/v1/partnerships")
@RequiredArgsConstructor
@Tag(name = "Partenariats B2B", description = "Gestion des Tiers (Fournisseurs, Partenaires)")
public class PartnershipController {

    private final ThirdPartyService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un Tiers", description = "Enregistre un nouveau fournisseur ou partenaire.")
    public Mono<ResponseEntity<ThirdPartyDTO.Response>> create(@Valid @RequestBody ThirdPartyDTO.Request request) {
        return service.create(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un Tiers", description = "Retourne le détail avec adresses et contacts.")
    public Mono<ResponseEntity<ThirdPartyDTO.Response>> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/organization/{orgId}")
    @Operation(summary = "Lister les Tiers d'une organisation")
    public Flux<ThirdPartyDTO.Response> getByOrg(@PathVariable UUID orgId) {
        return service.getByOrganization(orgId);
    }
}