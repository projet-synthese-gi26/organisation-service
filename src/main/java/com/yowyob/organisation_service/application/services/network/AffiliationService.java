package com.yowyob.organisation_service.application.services.network;

import com.yowyob.organisation_service.application.dtos.AffiliationDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain.AgencyAffiliation;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain.OrganizationActor;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.AgencyAffiliationRepository;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.OrganizationActorRepository;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AffiliationService {

    private final OrganizationActorRepository organizationActorRepository;
    private final AgencyAffiliationRepository agencyAffiliationRepository;
    private final OrganizationRepository organizationRepository; // Pour vérifier l'existence si besoin

    /**
     * Lier un acteur à une Organisation
     */
    @Transactional
    public Mono<AffiliationDTO.Response> affiliateToOrganization(AffiliationDTO.Request request) {
        return organizationActorRepository.findByOrganizationIdAndActorId(request.targetId(), request.actorId()) // Vérifier si methode repo existe, sinon à créer
                .hasElements()
                .flatMap(exists -> {
                    if (exists) return Mono.error(new ResponseStatusException(BAD_REQUEST, "Déjà affilié à cette organisation"));
                    
                    OrganizationActor link = OrganizationActor.builder()
                            .organizationId(request.targetId())
                            .actorId(request.actorId())
                            .type(request.type())
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return organizationActorRepository.save(link);
                })
                .map(saved -> new AffiliationDTO.Response(
                        saved.getId(), saved.getActorId(), saved.getOrganizationId(), saved.getType(), saved.getIsActive(), saved.getCreatedAt()
                ));
    }

    /**
     * Lier un acteur à une Agence
     */
    @Transactional
    public Mono<AffiliationDTO.Response> affiliateToAgency(AffiliationDTO.Request request) {
        AgencyAffiliation link = AgencyAffiliation.builder()
                .agencyId(request.targetId())
                .actorId(request.actorId())
                .type(request.type())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .organizationId(request.targetId())
                .build();

        return agencyAffiliationRepository.save(link)
                .map(saved -> new AffiliationDTO.Response(
                        saved.getId(), saved.getActorId(), saved.getAgencyId(), saved.getType(), saved.getIsActive(), saved.getCreatedAt()
                ));
    }
}