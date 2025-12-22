package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain.OrganizationDomain;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import java.util.UUID;

public interface OrganizationDomainRepository extends R2dbcRepository<OrganizationDomain, UUID> { }
