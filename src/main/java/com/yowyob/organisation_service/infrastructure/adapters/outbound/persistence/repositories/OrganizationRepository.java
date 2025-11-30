package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Organization;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrganizationRepository extends R2dbcRepository<Organization, UUID> {

    Mono<Organization> findByCode(String code);

    Mono<Boolean> existsByBusinessRegistrationNumber(String registrationNumber);
}