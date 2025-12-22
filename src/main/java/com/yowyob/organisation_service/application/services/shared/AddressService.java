package com.yowyob.organisation_service.application.services.shared;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.mappers.AddressMapper;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Address;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public Mono<AddressDTO.Response> createAddress(AddressDTO.Request request) {
        Address entity = addressMapper.toEntity(request);
        entity.setCreatedAt(LocalDateTime.now());
        return addressRepository.save(entity)
                .map(addressMapper::toResponse);
    }

    // Méthode polymorphique pour récupérer les adresses de n'importe qui (Org, Agency, Actor)
    public Flux<AddressDTO.Response> getAddressesByParent(UUID parentId, String parentType) {
        return addressRepository.findByAddressableIdAndAddressableType(parentId, parentType)
                .map(addressMapper::toResponse);
    }

    public Mono<AddressDTO.Response> updateAddress(UUID id, AddressDTO.Request request) {
        return addressRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Adresse non trouvée")))
                .flatMap(existing -> {
                    addressMapper.updateEntityFromRequest(request, existing);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return addressRepository.save(existing);
                })
                .map(addressMapper::toResponse);
    }

    public Mono<Void> deleteAddress(UUID id) {
        return addressRepository.deleteById(id); // Hard delete pour une adresse, ou soft selon besoin
    }
}