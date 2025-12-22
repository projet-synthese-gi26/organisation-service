package com.yowyob.organisation_service.application.services.crm;

import com.yowyob.organisation_service.application.dtos.ProspectDTO;
import com.yowyob.organisation_service.application.mappers.CrmMapper;
import com.yowyob.organisation_service.application.mappers.InteractionMapper;
import com.yowyob.organisation_service.application.services.shared.AddressService;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty.Prospect;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.InteractionRepository;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.ProspectRepository;
import lombok.RequiredArgsConstructor;
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
public class ProspectService {

    private final ProspectRepository repository;
    private final InteractionRepository interactionRepository;
    private final CrmMapper mapper;
    private final InteractionMapper interactionMapper;
    private final AddressService addressService;
    private final ContactService contactService;

    @Transactional
    public Mono<ProspectDTO.Response> createProspect(ProspectDTO.Request request) {
        Prospect entity = mapper.toProspectEntity(request);
        entity.setCode("PSP-" + System.currentTimeMillis());
        entity.setCreatedAt(LocalDateTime.now());
        if(entity.getName() == null) entity.setName(request.firstName() + " " + request.lastName());

        return repository.save(entity)
                .map(p -> mapper.toProspectResponse(p, null, null, null));
    }

    public Mono<ProspectDTO.Response> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Prospect non trouvé")))
                .flatMap(p -> Mono.zip(
                        interactionRepository.findByProspectId(p.getId()).map(interactionMapper::toResponse).collectList(),
                        addressService.getAddressesByParent(p.getId(), "PROSPECT").collectList(),
                        contactService.getContactsByParent(p.getId(), "PROSPECT").collectList()
                ).map(tuple -> mapper.toProspectResponse(p, tuple.getT1(), tuple.getT2(), tuple.getT3())));
    }
}