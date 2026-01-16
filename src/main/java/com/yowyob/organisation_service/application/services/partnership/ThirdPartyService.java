package com.yowyob.organisation_service.application.services.partnership;

import com.yowyob.organisation_service.application.dtos.ThirdPartyDTO;
import com.yowyob.organisation_service.application.mappers.ThirdPartyMapper;
import com.yowyob.organisation_service.application.services.shared.AddressService;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty.ThirdParty;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.ThirdPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.OrganizationRepository;

@Service
@RequiredArgsConstructor
public class ThirdPartyService {

    private final ThirdPartyRepository repository;
    private final OrganizationRepository organizationRepository; // <--- Injection
    private final ThirdPartyMapper mapper;
    private final AddressService addressService;
    private final ContactService contactService;

    @Transactional
    public Mono<ThirdPartyDTO.Response> create(ThirdPartyDTO.Request request) {
        return organizationRepository.existsById(request.organizationId())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Organisation invalide")))
                .flatMap(exists -> {
                    ThirdParty entity = mapper.toEntity(request);
                    entity.setCode("TP-" + System.currentTimeMillis());
                    entity.setCreatedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .map(mapper::toResponse);
    }

    public Mono<ThirdPartyDTO.Response> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Tiers non trouvé")))
                .flatMap(tp -> Mono.zip(
                        addressService.getAddressesByParent(tp.getId(), "THIRD_PARTY").collectList(),
                        contactService.getContactsByParent(tp.getId(), "THIRD_PARTY").collectList()
                ).map(tuple -> mapper.toRichResponse(tp, tuple.getT1(), tuple.getT2())));
    }

    public Flux<ThirdPartyDTO.Response> getByOrganization(UUID orgId) {
        return repository.findByOrganizationId(orgId)
                .map(mapper::toResponse); // Version simple pour liste
    }
}