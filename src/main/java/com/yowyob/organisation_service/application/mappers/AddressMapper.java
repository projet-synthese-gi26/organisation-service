package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Address;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    builder = @Builder(disableBuilder = true) // <--- AJOUTEZ CECI
)
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Address toEntity(AddressDTO.Request request);

    AddressDTO.Response toResponse(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addressableId", ignore = true)
    @Mapping(target = "addressableType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AddressDTO.Request request, @MappingTarget Address entity);
}