package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.InteractionDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Interaction;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InteractionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "interactionId", ignore = true) // Si champ technique interne
    Interaction toEntity(InteractionDTO.Request request);

    InteractionDTO.Response toResponse(Interaction entity);
}