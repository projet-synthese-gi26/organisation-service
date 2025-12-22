package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.BusinessDomainDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.domain.BusinessDomain;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusinessDomainMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    BusinessDomain toEntity(BusinessDomainDTO.Request request);

    BusinessDomainDTO.Response toResponse(BusinessDomain entity);
}