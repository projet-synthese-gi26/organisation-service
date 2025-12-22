package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Certification;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface CertificationRepository extends R2dbcRepository<Certification, UUID> {
    Flux<Certification> findByOrganizationId(UUID organizationId);
}