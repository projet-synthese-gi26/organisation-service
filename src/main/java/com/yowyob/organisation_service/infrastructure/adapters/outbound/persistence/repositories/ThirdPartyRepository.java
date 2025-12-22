package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty.ThirdParty;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface ThirdPartyRepository extends R2dbcRepository<ThirdParty, UUID> {
    Flux<ThirdParty> findByOrganizationId(UUID organizationId);
}