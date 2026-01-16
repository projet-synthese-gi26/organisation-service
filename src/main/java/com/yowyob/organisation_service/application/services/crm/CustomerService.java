package com.yowyob.organisation_service.application.services.crm;

import com.yowyob.organisation_service.application.dtos.CustomerDTO;
import com.yowyob.organisation_service.application.mappers.CrmMapper;
import com.yowyob.organisation_service.application.services.shared.AddressService;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.Customer;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.OrganizationRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final OrganizationRepository organizationRepository; // <--- Injection ajoutée
    private final CrmMapper mapper;
    private final AddressService addressService;
    private final ContactService contactService;

    @Transactional
    public Mono<CustomerDTO.Response> createCustomer(CustomerDTO.Request request) {
        // 1. Vérification d'existence de l'organisation
        return organizationRepository.existsById(request.organizationId())
                .filter(exists -> exists)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(NOT_FOUND, "Organisation invalide ou inexistante")))
                .flatMap(exists -> {
                    // 2. Logique de création originale
                    Customer entity = mapper.toCustomerEntity(request);
                    entity.setCode("CUST-" + System.currentTimeMillis());
                    entity.setCreatedAt(LocalDateTime.now());
                    if (entity.getName() == null)
                        entity.setName(request.firstName() + " " + request.lastName());

                    return repository.save(entity);
                })
                .map(c -> mapper.toCustomerResponse(c, null, null));
    }

    public Mono<CustomerDTO.Response> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Client non trouvé")))
                .flatMap(c -> Mono.zip(
                        addressService.getAddressesByParent(c.getId(), "CUSTOMER").collectList(),
                        contactService.getContactsByParent(c.getId(), "CUSTOMER").collectList())
                        .map(tuple -> mapper.toCustomerResponse(c, tuple.getT1(), tuple.getT2())));
    }

    public Flux<CustomerDTO.Response> getByOrganization(UUID orgId) {
        return repository.findByOrganizationId(orgId)
                .map(c -> mapper.toCustomerResponse(c, null, null));
    }
}