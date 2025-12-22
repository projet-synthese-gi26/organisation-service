package com.yowyob.organisation_service.application.services.references;

import com.yowyob.organisation_service.application.dtos.BusinessDomainDTO;
import com.yowyob.organisation_service.application.mappers.BusinessDomainMapper;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain.OrganizationDomain;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain.BusinessDomain;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.BusinessDomainRepository;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.OrganizationDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessDomainService {

    private final BusinessDomainRepository domainRepository;
    private final OrganizationDomainRepository organizationDomainRepository;
    private final BusinessDomainMapper mapper;

    @Transactional
    public Mono<BusinessDomainDTO.Response> createDomain(BusinessDomainDTO.Request request) {
        BusinessDomain entity = mapper.toEntity(request);
        entity.setCreatedAt(LocalDateTime.now());
        return domainRepository.save(entity).map(mapper::toResponse);
    }

    public Flux<BusinessDomainDTO.Response> getAllDomains() {
        return domainRepository.findAll().map(mapper::toResponse);
    }
    
    public Flux<BusinessDomainDTO.Response> getSubDomains(UUID parentId) {
        return domainRepository.findByParentId(parentId).map(mapper::toResponse);
    }

    // Lier une organisation à un domaine
    @Transactional
    public Mono<Void> linkOrganizationToDomain(UUID organizationId, UUID domainId) {
        OrganizationDomain link = OrganizationDomain.builder()
                .organizationId(organizationId)
                .domainId(domainId)
                .createdAt(LocalDateTime.now())
                .build();
        return organizationDomainRepository.save(link).then();
    }
}