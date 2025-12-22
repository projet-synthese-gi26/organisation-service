package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.ProposedActivityDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.ProposedActivity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProposedActivityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    ProposedActivity toEntity(ProposedActivityDTO.Request request);

    ProposedActivityDTO.Response toResponse(ProposedActivity entity);
}