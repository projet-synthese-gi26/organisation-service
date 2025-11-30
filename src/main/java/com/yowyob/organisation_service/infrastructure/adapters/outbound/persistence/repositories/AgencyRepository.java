package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Agency;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface AgencyRepository extends R2dbcRepository<Agency, UUID> {

    Flux<Agency> findByOrganizationId(UUID organizationId);

    Mono<Agency> findByCode(String code);

    Mono<Agency> findByOrganizationIdAndIsHeadquarterTrue(UUID organizationId);
}