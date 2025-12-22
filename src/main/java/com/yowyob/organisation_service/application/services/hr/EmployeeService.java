package com.yowyob.organisation_service.application.services.hr;

import com.yowyob.organisation_service.application.dtos.EmployeeDTO;
import com.yowyob.organisation_service.application.mappers.EmployeeMapper;
import com.yowyob.organisation_service.application.services.shared.AddressService;
import com.yowyob.organisation_service.application.services.shared.ContactService;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.Employee;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.EmployeeRepository;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.OrganizationRepository;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository; // Pour vérifier l'existence
    private final EmployeeMapper employeeMapper;
    
    private final AddressService addressService;
    private final ContactService contactService;

    @Transactional
    public Mono<EmployeeDTO.Response> createEmployee(EmployeeDTO.Request request) {
        return organizationRepository.existsById(request.organizationId())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Organisation parente introuvable")))
                .flatMap(exists -> {
                    Employee entity = employeeMapper.toEntity(request);
                    
                    // Code génération: EMP-{Nom}-{Timestamp}
                    String code = "EMP-" + (request.lastName() != null ? request.lastName().toUpperCase() : "X") 
                                  + "-" + System.currentTimeMillis() % 10000;
                    entity.setCode(code);
                    entity.setCreatedAt(LocalDateTime.now());
                    
                    // Construction du "name" si vide
                    if(entity.getName() == null) {
                        entity.setName(request.firstName() + " " + request.lastName());
                    }

                    return employeeRepository.save(entity);
                })
                .map(employeeMapper::toResponse);
    }

    public Mono<EmployeeDTO.Response> getEmployeeById(UUID id) {
        return employeeRepository.findById(id)
                .filter(e -> e.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Employé introuvable")))
                .flatMap(emp -> Mono.zip(
                        addressService.getAddressesByParent(emp.getId(), "EMPLOYEE").collectList(),
                        contactService.getContactsByParent(emp.getId(), "EMPLOYEE").collectList()
                ).map(tuple -> employeeMapper.toRichResponse(emp, tuple.getT1(), tuple.getT2())));
    }

    public Flux<EmployeeDTO.Response> getEmployeesByOrganization(UUID organizationId) {
        return employeeRepository.findByOrganizationId(organizationId)
                .filter(e -> e.getDeletedAt() == null)
                .map(employeeMapper::toResponse);
    }

    @Transactional
    public Mono<EmployeeDTO.Response> updateEmployee(UUID id, EmployeeDTO.Request request) {
        return employeeRepository.findById(id)
                .filter(e -> e.getDeletedAt() == null)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Employé introuvable")))
                .flatMap(existing -> {
                    employeeMapper.updateEntityFromRequest(request, existing);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return employeeRepository.save(existing);
                })
                .map(employeeMapper::toResponse);
    }

    public Mono<Void> deleteEmployee(UUID id) {
        return employeeRepository.findById(id)
                .flatMap(existing -> {
                    existing.setDeletedAt(LocalDateTime.now());
                    return employeeRepository.save(existing);
                }).then();
    }
}