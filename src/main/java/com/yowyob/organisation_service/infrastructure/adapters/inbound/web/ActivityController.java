package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.ProposedActivityDTO;
import com.yowyob.organisation_service.application.services.shared.ProposedActivityService;
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
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
@Tag(name = "Activités & Services", description = "Ce que l'organisation vend")
public class ActivityController {

    private final ProposedActivityService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<ProposedActivityDTO.Response>> create(@Valid @RequestBody ProposedActivityDTO.Request request) {
        return service.createActivity(request).map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/organization/{orgId}")
    public Flux<ProposedActivityDTO.Response> getByOrg(@PathVariable UUID orgId) {
        return service.getByOrganization(orgId);
    }
}