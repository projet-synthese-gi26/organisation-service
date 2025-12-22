package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.CustomerDTO;
import com.yowyob.organisation_service.application.dtos.InteractionDTO;
import com.yowyob.organisation_service.application.dtos.ProspectDTO;
import com.yowyob.organisation_service.application.services.crm.CustomerService;
import com.yowyob.organisation_service.application.services.crm.InteractionService;
import com.yowyob.organisation_service.application.services.crm.ProspectService;
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
@RequestMapping("/api/v1/crm")
@RequiredArgsConstructor
@Tag(name = "CRM", description = "Gestion Clients, Prospects et Interactions")
public class CrmController {

    private final CustomerService customerService;
    private final ProspectService prospectService;
    private final InteractionService interactionService;

    // --- CLIENTS ---

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un Client")
    public Mono<ResponseEntity<CustomerDTO.Response>> createCustomer(@Valid @RequestBody CustomerDTO.Request request) {
        return customerService.createCustomer(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/customers/{id}")
    public Mono<ResponseEntity<CustomerDTO.Response>> getCustomer(@PathVariable UUID id) {
        return customerService.getById(id).map(ResponseEntity::ok);
    }

    @GetMapping("/customers/organization/{orgId}")
    public Flux<CustomerDTO.Response> getCustomersByOrg(@PathVariable UUID orgId) {
        return customerService.getByOrganization(orgId);
    }

    // --- PROSPECTS ---

    @PostMapping("/prospects")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un Prospect")
    public Mono<ResponseEntity<ProspectDTO.Response>> createProspect(@Valid @RequestBody ProspectDTO.Request request) {
        return prospectService.createProspect(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/prospects/{id}")
    @Operation(summary = "Récupérer un Prospect", description = "Inclut l'historique des interactions.")
    public Mono<ResponseEntity<ProspectDTO.Response>> getProspect(@PathVariable UUID id) {
        return prospectService.getById(id).map(ResponseEntity::ok);
    }

    // --- INTERACTIONS ---

    @PostMapping("/interactions")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter une Interaction", description = "Enregistre une note sur un prospect et notifie via Kafka.")
    public Mono<ResponseEntity<InteractionDTO.Response>> addInteraction(@Valid @RequestBody InteractionDTO.Request request) {
        return interactionService.addInteraction(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/interactions/prospect/{id}")
    @Operation(summary = "Historique d'un prospect")
    public Flux<InteractionDTO.Response> getHistory(@PathVariable UUID id) {
        return interactionService.getHistory(id);
    }
}