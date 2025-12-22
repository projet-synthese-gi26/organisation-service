package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.dtos.BusinessActorDTO;
import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.BusinessActor;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusinessActorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "isVerified", constant = "false")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    BusinessActor toEntity(BusinessActorDTO.Request request);

    default BusinessActorDTO.Response toResponse(BusinessActor entity) {
        return toRichResponse(entity, Collections.emptyList(), Collections.emptyList());
    }

    default BusinessActorDTO.Response toRichResponse(BusinessActor entity, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
        if (entity == null) return null;

        return new BusinessActorDTO.Response(
                entity.getId(),
                entity.getCode(),
                entity.getAuthUserId(),
                entity.getOrganizationId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getName(),
                entity.getEmail(),
                entity.getType(),
                entity.getRole(),
                entity.getIsIndividual(),
                entity.getIsAvailable(),
                entity.getIsVerified(),
                entity.getIsActive(),
                map(entity.getQualifications()),
                map(entity.getPaymentMethods()),
                addresses != null ? addresses : Collections.emptyList(),
                contacts != null ? contacts : Collections.emptyList(),
                entity.getCreatedAt()
        );
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(BusinessActorDTO.Request request, @MappingTarget BusinessActor entity);

    // Helpers tableaux
    default List<String> map(String[] value) {
        return value == null ? List.of() : Arrays.asList(value);
    }
    default String[] map(List<String> value) {
        return value == null ? new String[0] : value.toArray(new String[0]);
    }
}