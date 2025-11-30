package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories;


import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Address;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface AddressRepository extends R2dbcRepository<Address, UUID> {

    Flux<Address> findByAddressableIdAndAddressableType(UUID addressableId, String addressableType);
}