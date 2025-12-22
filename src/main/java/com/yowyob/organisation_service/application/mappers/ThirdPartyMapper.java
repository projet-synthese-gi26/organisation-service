package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.application.dtos.ThirdPartyDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty.ThirdParty;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ThirdPartyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "classification", ignore = true) // JSON handling complex, ignored for simple mapping
    ThirdParty toEntity(ThirdPartyDTO.Request request);

    // Méthode simple
    default ThirdPartyDTO.Response toResponse(ThirdParty entity) {
        return toRichResponse(entity, Collections.emptyList(), Collections.emptyList());
    }

    // Méthode riche
    default ThirdPartyDTO.Response toRichResponse(ThirdParty entity, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
        if (entity == null) return null;

        return new ThirdPartyDTO.Response(
                entity.getId(),
                entity.getCode(),
                entity.getOrganizationId(),
                entity.getName(),
                entity.getType(),
                entity.getTaxNumber(),
                entity.getAuthorizedCreditLimit(),
                entity.getVatSubject(),
                entity.getEnabled(),
                addresses != null ? addresses : Collections.emptyList(),
                contacts != null ? contacts : Collections.emptyList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    // Helpers pour les tableaux String[] <-> List<String>
    default List<String> map(String[] value) { return value == null ? List.of() : Arrays.asList(value); }
    default String[] map(List<String> value) { return value == null ? new String[0] : value.toArray(new String[0]); }
}