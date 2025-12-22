package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.BusinessActorDTO;
import com.yowyob.organisation_service.application.services.hr.BusinessActorService;
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
@RequestMapping("/api/v1/business-actors")
@RequiredArgsConstructor
@Tag(name = "Gestion des Business Actors", description = "Propriétaires, Indépendants...")
public class BusinessActorController {

    private final BusinessActorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<BusinessActorDTO.Response>> create(@Valid @RequestBody BusinessActorDTO.Request request) {
        return service.createActor(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BusinessActorDTO.Response>> getById(@PathVariable UUID id) {
        return service.getActorById(id).map(ResponseEntity::ok);
    }
    
    @GetMapping
    public Flux<BusinessActorDTO.Response> getAll() {
        return service.getAll();
    }
}