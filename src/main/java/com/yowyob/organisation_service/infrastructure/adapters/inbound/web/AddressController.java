package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.services.shared.AddressService;
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
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "Gestion des Adresses", description = "Endpoints polymorphiques pour gérer les adresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter une adresse", description = "Lier une adresse à une Organisation, Agence ou Acteur via addressableId et addressableType.")
    public Mono<ResponseEntity<AddressDTO.Response>> create(@Valid @RequestBody AddressDTO.Request request) {
        return addressService.createAddress(request)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une adresse")
    public Mono<ResponseEntity<AddressDTO.Response>> update(@PathVariable UUID id, @Valid @RequestBody AddressDTO.Request request) {
        return addressService.updateAddress(id, request)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return addressService.deleteAddress(id)
                .map(r -> ResponseEntity.noContent().<Void>build());
    }
}