package com.yowyob.organisation_service.application.services.shared;

import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.application.mappers.ContactMapper;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Contact;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional
    public Mono<ContactDTO.Response> createContact(ContactDTO.Request request) {
        Contact entity = contactMapper.toEntity(request);
        entity.setCreatedAt(LocalDateTime.now());
        return contactRepository.save(entity)
                .map(contactMapper::toResponse);
    }

    public Flux<ContactDTO.Response> getContactsByParent(UUID parentId, String parentType) {
        return contactRepository.findByContactableIdAndContactableType(parentId, parentType)
                .map(contactMapper::toResponse);
    }

    public Mono<ContactDTO.Response> updateContact(UUID id, ContactDTO.Request request) {
        return contactRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Contact non trouvé")))
                .flatMap(existing -> {
                    contactMapper.updateEntityFromRequest(request, existing);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return contactRepository.save(existing);
                })
                .map(contactMapper::toResponse);
    }

    public Mono<Void> deleteContact(UUID id) {
        return contactRepository.deleteById(id);
    }
}