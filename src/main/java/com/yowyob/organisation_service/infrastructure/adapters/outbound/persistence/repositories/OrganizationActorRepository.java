package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain.OrganizationActor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface OrganizationActorRepository extends R2dbcRepository<OrganizationActor, UUID> {

    // This method belongs here because it queries the 'organization_actor' table
    Flux<OrganizationActor> findByOrganizationIdAndActorId(UUID organizationId, UUID actorId);
    
}