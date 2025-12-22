package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.ProposedActivity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface ProposedActivityRepository extends R2dbcRepository<ProposedActivity, UUID> {
    Flux<ProposedActivity> findByOrganizationId(UUID organizationId);
}