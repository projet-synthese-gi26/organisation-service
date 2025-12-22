package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.BusinessActor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import java.util.UUID;

public interface BusinessActorRepository extends R2dbcRepository<BusinessActor, UUID> {
    // Standard CRUD methods are provided by R2dbcRepository
}