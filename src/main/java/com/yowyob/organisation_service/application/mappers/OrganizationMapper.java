package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.application.dtos.OrganizationDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Organization;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Organization toEntity(OrganizationDTO.Request request);

    // Méthode simple
    default OrganizationDTO.Response toResponse(Organization org) {
        return toRichResponse(org, Collections.emptyList(), Collections.emptyList());
    }

    // Méthode riche
    default OrganizationDTO.Response toRichResponse(Organization org, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
        if (org == null) return null;
        
        return new OrganizationDTO.Response(
            org.getId(),
            org.getCode(),
            org.getService(),
            org.getBusinessActorId(),
            org.getIsIndividualBusiness(),
            org.getEmail(),
            org.getShortName(),
            org.getLongName(),
            org.getDescription(),
            org.getLogoUri(),
            org.getLogoId(),
            org.getWebsiteUrl(),
            org.getSocialNetwork(),
            org.getBusinessRegistrationNumber(),
            org.getTaxNumber(),
            org.getCapitalShare(),
            org.getCeoName(),
            org.getYearFounded(),
            // L'ordre ici doit matcher EXACTEMENT le record OrganizationDTO.Response
            addresses != null ? addresses : Collections.emptyList(),
            contacts != null ? contacts : Collections.emptyList(),
            map(org.getKeywords()),
            
            org.getNumberOfEmployees(),
            org.getLegalForm(),
            org.getIsActive(),
            org.getStatus(),
            org.getCreatedAt(),
            org.getUpdatedAt()
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(OrganizationDTO.Request request, @MappingTarget Organization entity);

    default List<String> map(String[] value) {
        return value == null ? List.of() : Arrays.asList(value);
    }

    default String[] map(List<String> value) {
        return value == null ? new String[0] : value.toArray(new String[0]);
    }
}