package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.application.dtos.EmployeeDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.Employee;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "contacts", ignore = true) // Ignore les IDs stockés en tableau pour l'instant
    @Mapping(target = "addresses", ignore = true)
    Employee toEntity(EmployeeDTO.Request request);

    // Méthode par défaut pour usage interne
    default EmployeeDTO.Response toResponse(Employee entity) {
        return toRichResponse(entity, Collections.emptyList(), Collections.emptyList());
    }

    // Méthode enrichie
    default EmployeeDTO.Response toRichResponse(Employee entity, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
        if (entity == null) return null;

        return new EmployeeDTO.Response(
                entity.getId(),
                entity.getCode(),
                entity.getOrganizationId(),
                entity.getAuthUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getRole(),
                entity.getDepartment(),
                entity.getIsManager(),
                entity.getPhotoUri(),
                entity.getDescription(),
                entity.getGender(),
                entity.getNationality(),
                entity.getBirthDate(),
                entity.getProfession(),
                addresses != null ? addresses : Collections.emptyList(),
                contacts != null ? contacts : Collections.emptyList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(EmployeeDTO.Request request, @MappingTarget Employee entity);
}