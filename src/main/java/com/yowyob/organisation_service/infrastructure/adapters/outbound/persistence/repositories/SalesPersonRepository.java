package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;


import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.SalesPerson;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface SalesPersonRepository extends R2dbcRepository<SalesPerson, UUID> {

    Flux<SalesPerson> findByOrganizationId(UUID organizationId);
}