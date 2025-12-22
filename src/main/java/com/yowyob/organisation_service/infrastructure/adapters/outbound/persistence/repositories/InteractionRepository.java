package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Interaction;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface InteractionRepository extends R2dbcRepository<Interaction, UUID> {
    Flux<Interaction> findByProspectId(UUID prospectId);
}