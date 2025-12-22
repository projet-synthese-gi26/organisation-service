package com.yowyob.organisation_service.application.services.shared;

import com.yowyob.organisation_service.application.dtos.ProposedActivityDTO;
import com.yowyob.organisation_service.application.mappers.ProposedActivityMapper;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.ProposedActivity;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.ProposedActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProposedActivityService {

    private final ProposedActivityRepository repository;
    private final ProposedActivityMapper mapper;

    @Transactional
    public Mono<ProposedActivityDTO.Response> createActivity(ProposedActivityDTO.Request request) {
        ProposedActivity entity = mapper.toEntity(request);
        entity.setCreatedAt(LocalDateTime.now());
        return repository.save(entity).map(mapper::toResponse);
    }

    public Flux<ProposedActivityDTO.Response> getByOrganization(UUID organizationId) {
        return repository.findByOrganizationId(organizationId).map(mapper::toResponse);
    }
}