package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@Tag(name = "Gestion des Contacts", description = "Endpoints polymorphiques pour gérer les contacts")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<ContactDTO.Response>> create(@Valid @RequestBody ContactDTO.Request request) {
        return contactService.createContact(request)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ContactDTO.Response>> update(@PathVariable UUID id, @Valid @RequestBody ContactDTO.Request request) {
        return contactService.updateContact(id, request)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return contactService.deleteContact(id)
                .map(r -> ResponseEntity.noContent().<Void>build());
    }
}