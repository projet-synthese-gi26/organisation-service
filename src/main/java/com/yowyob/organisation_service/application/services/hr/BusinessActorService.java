package com.yowyob.organisation_service.application.services.hr;

import com.yowyob.organisation_service.application.dtos.BusinessActorDTO;
import com.yowyob.organisation_service.application.mappers.BusinessActorMapper;
import com.yowyob.organisation_service.application.services.shared.AddressService;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.BusinessActor;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.BusinessActorRepository;
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
public class BusinessActorService {

    private final BusinessActorRepository repository;
    private final BusinessActorMapper mapper;
    private final AddressService addressService;
    private final ContactService contactService;

    @Transactional
    public Mono<BusinessActorDTO.Response> createActor(BusinessActorDTO.Request request) {
        BusinessActor entity = mapper.toEntity(request);
        entity.setCode("BA-" + System.currentTimeMillis());
        entity.setCreatedAt(LocalDateTime.now());
        if(entity.getName() == null) entity.setName(request.firstName() + " " + request.lastName());

        return repository.save(entity)
                .map(mapper::toResponse);
    }

    public Mono<BusinessActorDTO.Response> getActorById(UUID id) {
        return repository.findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Acteur introuvable")))
                .flatMap(actor -> Mono.zip(
                        addressService.getAddressesByParent(actor.getId(), "BUSINESS_ACTOR").collectList(),
                        contactService.getContactsByParent(actor.getId(), "BUSINESS_ACTOR").collectList()
                ).map(tuple -> mapper.toRichResponse(actor, tuple.getT1(), tuple.getT2())));
    }
    
    public Flux<BusinessActorDTO.Response> getAll() {
         return repository.findAll()
                 .filter(a -> a.getDeletedAt() == null)
                 .map(mapper::toResponse);
    }
}