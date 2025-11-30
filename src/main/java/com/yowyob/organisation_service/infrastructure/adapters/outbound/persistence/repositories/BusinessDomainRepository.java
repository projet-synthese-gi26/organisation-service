package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;


import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.reference.BusinessDomain;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface BusinessDomainRepository extends R2dbcRepository<BusinessDomain, UUID> {

    Flux<BusinessDomain> findByParentId(UUID parentId);
}