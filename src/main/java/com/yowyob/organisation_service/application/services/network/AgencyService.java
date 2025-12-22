package com.yowyob.organisation_service.application.services.network;

import com.yowyob.organisation_service.application.dtos.AgencyDTO;
import com.yowyob.organisation_service.application.mappers.AgencyMapper;
import com.yowyob.organisation_service.application.services.shared.AddressService;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Agency;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.AgencyRepository;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.OrganizationRepository;
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
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final OrganizationRepository organizationRepository;
    private final AgencyMapper agencyMapper;
    
    private final AddressService addressService;
    private final ContactService contactService;

    @Transactional
    public Mono<AgencyDTO.Response> createAgency(AgencyDTO.Request request) {
        return organizationRepository.existsById(request.organizationId())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "L'organisation parente n'existe pas")))
                .flatMap(exists -> {
                    Agency entity = agencyMapper.toEntity(request);
                    
                    // Code: AGC-{OrgIdShort}-{Timestamp}
                    String code = "AGC-" + System.currentTimeMillis() % 100000;
                    entity.setCode(code);
                    entity.setCreatedAt(LocalDateTime.now());
                    
                    return agencyRepository.save(entity);
                })
                .map(agencyMapper::toResponse);
    }

    public Mono<AgencyDTO.Response> getAgencyById(UUID id) {
        return agencyRepository.findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Agence non trouvée")))
                .flatMap(agency -> Mono.zip(
                        addressService.getAddressesByParent(agency.getId(), "AGENCY").collectList(),
                        contactService.getContactsByParent(agency.getId(), "AGENCY").collectList()
                ).map(tuple -> agencyMapper.toRichResponse(agency, tuple.getT1(), tuple.getT2())));
    }

    public Flux<AgencyDTO.Response> getAgenciesByOrganization(UUID organizationId) {
        return agencyRepository.findByOrganizationId(organizationId)
                .filter(a -> a.getDeletedAt() == null)
                .map(agencyMapper::toResponse); // Liste simple pour performance
    }
    
    // Update & Delete 
    public Mono<AgencyDTO.Response> updateAgency(UUID id, AgencyDTO.Request request) {
        return agencyRepository.findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Agence introuvable")))
                .flatMap(existing -> {
                    agencyMapper.updateEntityFromRequest(request, existing);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return agencyRepository.save(existing);
                })
                .map(agencyMapper::toResponse);

    }

    public Mono<AgencyDTO.Response> deleteAgency(UUID id) {
        return agencyRepository.findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Agence introuvable")))
                .flatMap(existing -> {
                    existing.setDeletedAt(LocalDateTime.now());
                    return agencyRepository.save(existing);
                })
                .map(agencyMapper::toResponse);
    }
}