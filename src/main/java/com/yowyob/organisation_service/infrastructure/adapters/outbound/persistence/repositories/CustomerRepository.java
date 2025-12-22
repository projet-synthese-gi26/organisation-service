package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface CustomerRepository extends R2dbcRepository<Customer, UUID> {
    Flux<Customer> findByOrganizationId(UUID organizationId);
}