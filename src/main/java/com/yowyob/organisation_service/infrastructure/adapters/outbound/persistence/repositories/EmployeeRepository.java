package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;


import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.Employee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface EmployeeRepository extends R2dbcRepository<Employee, UUID> {


    Flux<Employee> findByOrganizationId(UUID organizationId);

    Mono<Employee> findByAuthUserId(UUID authUserId);

    Mono<Employee> findByCode(String code);
}
