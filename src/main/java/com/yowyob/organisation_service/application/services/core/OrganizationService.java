package com.yowyob.organisation_service.application.services.core;

import com.yowyob.organisation_service.application.dtos.OrganizationDTO;
import com.yowyob.organisation_service.application.services.shared.AddressService;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import com.yowyob.organisation_service.application.mappers.OrganizationMapper;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Organization;
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
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    private final AddressService addressService;
    private final ContactService contactService;

    @Transactional
    public Mono<OrganizationDTO.Response> createOrganization(OrganizationDTO.Request request) {
        log.info("Tentative de création d'organisation: {}", request.longName());

        Organization entity = organizationMapper.toEntity(request);

        // Génération du code
        String prefix = (request.shortName() != null && request.shortName().length() >= 3)
                ? request.shortName().substring(0, 3).toUpperCase()
                : (request.shortName() != null ? request.shortName().toUpperCase() : "ORG");
        
        String generatedCode = String.format("ORG-%s-%d", prefix, System.currentTimeMillis() % 100000);
        entity.setCode(generatedCode);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return organizationRepository.save(entity)
                // Ici on utilise la méthode simple car on n'a pas encore d'adresses/contacts
                .map(organizationMapper::toResponse); 
    }

    public Flux<OrganizationDTO.Response> getAllOrganizations() {
        return organizationRepository.findAll()
                .filter(org -> org.getDeletedAt() == null)
                // Ici aussi, on charge la version simple pour éviter de faire trop de requêtes SQL pour une liste
                .map(organizationMapper::toResponse); 
    }

    // C'est ICI qu'on charge les données riches (Adresses + Contacts)
    public Mono<OrganizationDTO.Response> getOrganizationById(UUID id) {
        return organizationRepository.findById(id)
                .filter(org -> org.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Organisation non trouvée")))
                .flatMap(org -> 
                    Mono.zip(
                        addressService.getAddressesByParent(org.getId(), "ORGANIZATION").collectList(),
                        contactService.getContactsByParent(org.getId(), "ORGANIZATION").collectList()
                    )
                    .map(tuple -> organizationMapper.toRichResponse(org, tuple.getT1(), tuple.getT2()))
                );
    }

    @Transactional
    public Mono<OrganizationDTO.Response> updateOrganization(UUID id, OrganizationDTO.Request request) {
        return organizationRepository.findById(id)
                .filter(org -> org.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Organisation introuvable pour mise à jour")))
                .flatMap(existingOrg -> {
                    organizationMapper.updateEntityFromRequest(request, existingOrg);
                    existingOrg.setUpdatedAt(LocalDateTime.now());
                    return organizationRepository.save(existingOrg);
                })
                // Maintenant cette ligne fonctionne car toResponse(Organization) existe dans le mapper
                .map(organizationMapper::toResponse) 
                .doOnSuccess(org -> log.info("Organisation mise à jour : {}", id));
    }

    @Transactional
    public Mono<Void> deleteOrganization(UUID id) {
        return organizationRepository.findById(id)
                .filter(org -> org.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Organisation introuvable pour suppression")))
                .flatMap(existingOrg -> {
                    existingOrg.setDeletedAt(LocalDateTime.now());
                    existingOrg.setIsActive(false);
                    existingOrg.setStatus("DELETED");
                    return organizationRepository.save(existingOrg);
                })
                .doOnSuccess(v -> log.info("Organisation supprimée (soft delete) : {}", id))
                .then();
    }
}