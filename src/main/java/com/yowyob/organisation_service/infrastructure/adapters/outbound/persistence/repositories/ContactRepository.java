package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;


import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Contact;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface ContactRepository extends R2dbcRepository<Contact, UUID> {

    Flux<Contact> findByContactableIdAndContactableType(UUID contactableId, String contactableType);
}