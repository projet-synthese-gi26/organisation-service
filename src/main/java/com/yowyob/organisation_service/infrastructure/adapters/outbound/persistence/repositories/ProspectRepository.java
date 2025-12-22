package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty.Prospect;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface ProspectRepository extends R2dbcRepository<Prospect, UUID> {
    Flux<Prospect> findByOrganizationId(UUID organizationId);
}