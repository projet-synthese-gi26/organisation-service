package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.OrganizationDTO;
import com.yowyob.organisation_service.application.services.core.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@Tag(name = "Gestion des Organisations", description = "Endpoints pour gérer le cycle de vie des organisations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une organisation", description = "Crée une nouvelle organisation. Le code est généré automatiquement.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Organisation créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides (ex: champs manquants)")
    })
    public Mono<ResponseEntity<OrganizationDTO.Response>> create(@Valid @RequestBody OrganizationDTO.Request request) {
        return organizationService.createOrganization(request)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping
    @Operation(summary = "Lister les organisations", description = "Récupère toutes les organisations actives.")
    public Flux<OrganizationDTO.Response> getAll() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("/owner/{businessActorId}")
    @Operation(summary = "Organisations d'un propriétaire", description = "Récupère les organisations liées à un Business Actor.")
    public Flux<OrganizationDTO.Response> getByOwner(@PathVariable UUID businessActorId) {
        return organizationService.getOrganizationsByOwner(businessActorId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer par ID", description = "Retourne les détails d'une organisation spécifique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Non trouvé")
    })
    public Mono<ResponseEntity<OrganizationDTO.Response>> getById(@PathVariable UUID id) {
        return organizationService.getOrganizationById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour", description = "Met à jour partiellement ou totalement une organisation.")
    public Mono<ResponseEntity<OrganizationDTO.Response>> update(@PathVariable UUID id,
            @Valid @RequestBody OrganizationDTO.Request request) {
        return organizationService.updateOrganization(id, request)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer (Soft Delete)", description = "Marque l'organisation comme supprimée sans effacer la ligne en base.")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return organizationService.deleteOrganization(id)
                .map(r -> ResponseEntity.noContent().<Void>build());
    }
}