package com.yowyob.organisation_service.application.mappers;

import com.yowyob.organisation_service.application.dtos.*;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.actor.Customer;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty.Prospect;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CrmMapper {

    // --- Customer ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Customer toCustomerEntity(CustomerDTO.Request request);

    default CustomerDTO.Response toCustomerResponse(Customer entity, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
        if(entity == null) return null;
        return new CustomerDTO.Response(
            entity.getId(), entity.getCode(), entity.getOrganizationId(), 
            entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhoneNumber(), 
            entity.getAmountPaid(), entity.getIsVerified(),
            addresses != null ? addresses : Collections.emptyList(),
            contacts != null ? contacts : Collections.emptyList(),
            entity.getCreatedAt()
        );
    }

    // --- Prospect ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Prospect toProspectEntity(ProspectDTO.Request request);

    default ProspectDTO.Response toProspectResponse(Prospect entity, List<InteractionDTO.Response> interactions, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
        if(entity == null) return null;
        return new ProspectDTO.Response(
            entity.getId(), entity.getCode(), entity.getOrganizationId(),
            entity.getFirstName(), entity.getLastName(), entity.getInterestLevel(),
            interactions != null ? interactions : Collections.emptyList(),
            addresses != null ? addresses : Collections.emptyList(),
            contacts != null ? contacts : Collections.emptyList(),
            entity.getCreatedAt()
        );
    }
}