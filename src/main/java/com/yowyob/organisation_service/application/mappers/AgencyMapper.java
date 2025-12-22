package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.dtos.AgencyDTO;
import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Agency;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AgencyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "ownerId", ignore = true) // À gérer séparément si besoin
    Agency toEntity(AgencyDTO.Request request);

    default AgencyDTO.Response toResponse(Agency entity) {
        return toRichResponse(entity, Collections.emptyList(), Collections.emptyList());
    }

    default AgencyDTO.Response toRichResponse(Agency entity, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
        if (entity == null) return null;

        return new AgencyDTO.Response(
                entity.getId(),
                entity.getCode(),
                entity.getOrganizationId(),
                entity.getManagerId(),
                entity.getName(),
                entity.getShortName(),
                entity.getLongName(),
                entity.getIsHeadquarter(),
                entity.getIsPublic(),
                entity.getIsActive(),
                entity.getLocation(),
                entity.getCountry(),
                entity.getCity(),
                entity.getOpenTime(),
                entity.getCloseTime(),
                entity.getEmail(),
                entity.getPhone(),
                addresses != null ? addresses : Collections.emptyList(),
                contacts != null ? contacts : Collections.emptyList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AgencyDTO.Request request, @MappingTarget Agency entity);

    default List<String> map(String[] value) {
        return value == null ? List.of() : Arrays.asList(value);
    }
    default String[] map(List<String> value) {
        return value == null ? new String[0] : value.toArray(new String[0]);
    }
}