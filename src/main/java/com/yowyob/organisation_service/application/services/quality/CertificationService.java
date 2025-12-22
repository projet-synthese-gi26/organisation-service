package com.yowyob.organisation_service.application.services.quality;

import com.yowyob.organisation_service.application.dtos.CertificationDTO;
import com.yowyob.organisation_service.application.mappers.CertificationMapper;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Certification;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository repository;
    private final CertificationMapper mapper;

    @Transactional
    public Mono<CertificationDTO.Response> addCertification(CertificationDTO.Request request) {
        Certification entity = mapper.toEntity(request);
        entity.setCreatedAt(LocalDateTime.now());
        return repository.save(entity).map(mapper::toResponse);
    }

    public Flux<CertificationDTO.Response> getByOrganization(UUID organizationId) {
        return repository.findByOrganizationId(organizationId).map(mapper::toResponse);
    }
}