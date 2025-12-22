package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.CertificationDTO;
import com.yowyob.organisation_service.application.services.quality.CertificationService;
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
@RequestMapping("/api/v1/certifications")
@RequiredArgsConstructor
@Tag(name = "Certifications", description = "Normes et Labels")
public class CertificationController {

    private final CertificationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<CertificationDTO.Response>> create(@Valid @RequestBody CertificationDTO.Request request) {
        return service.addCertification(request).map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/organization/{orgId}")
    public Flux<CertificationDTO.Response> getByOrg(@PathVariable UUID orgId) {
        return service.getByOrganization(orgId);
    }
}