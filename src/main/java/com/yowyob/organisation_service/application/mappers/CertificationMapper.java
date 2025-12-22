package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.CertificationDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Certification;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CertificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Certification toEntity(CertificationDTO.Request request);

    CertificationDTO.Response toResponse(Certification entity);
}