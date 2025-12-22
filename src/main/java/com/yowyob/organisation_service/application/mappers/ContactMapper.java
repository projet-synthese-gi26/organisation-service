package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared.Contact;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isEmailVerified", constant = "false")
    @Mapping(target = "isPhoneNumberVerified", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Contact toEntity(ContactDTO.Request request);

    ContactDTO.Response toResponse(Contact contact);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contactableId", ignore = true)
    @Mapping(target = "contactableType", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ContactDTO.Request request, @MappingTarget Contact entity);
}