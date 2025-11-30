package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;


import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.association.OrganizationActor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface OrganizationActorRepository extends R2dbcRepository<OrganizationActor, UUID> {


    Flux<OrganizationActor> findByActorId(UUID actorId);


    Flux<OrganizationActor> findByOrganizationIdAndType(UUID organizationId, String type);


    Mono<Void> deleteByOrganizationIdAndActorId(UUID organizationId, UUID actorId);
}