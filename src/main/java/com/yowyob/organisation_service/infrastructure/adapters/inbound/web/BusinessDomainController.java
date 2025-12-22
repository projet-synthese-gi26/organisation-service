package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.BusinessDomainDTO;
import com.yowyob.organisation_service.application.services.references.BusinessDomainService;
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
@RequestMapping("/api/v1/business-domains")
@RequiredArgsConstructor
@Tag(name = "Référentiel Domaines", description = "Secteurs d'activité")
public class BusinessDomainController {

    private final BusinessDomainService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<BusinessDomainDTO.Response>> create(@Valid @RequestBody BusinessDomainDTO.Request request) {
        return service.createDomain(request).map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping
    public Flux<BusinessDomainDTO.Response> getAll() {
        return service.getAllDomains();
    }

    @PostMapping("/{domainId}/link/{orgId}")
    @Operation(summary = "Lier une organisation à un domaine")
    public Mono<ResponseEntity<Void>> link(@PathVariable UUID domainId, @PathVariable UUID orgId) {
        return service.linkOrganizationToDomain(orgId, domainId)
                .map(v -> ResponseEntity.ok().build());
    }
}