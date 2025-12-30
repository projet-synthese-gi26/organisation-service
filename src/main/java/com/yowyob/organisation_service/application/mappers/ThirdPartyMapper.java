package com.yowyob.organisation_service.application.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yowyob.organisation_service.application.dtos.AddressDTO;
import com.yowyob.organisation_service.application.dtos.ContactDTO;
import com.yowyob.organisation_service.application.dtos.ThirdPartyDTO;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty.ThirdParty;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    builder = @Builder(disableBuilder = true) // Désactive le builder pour éviter les conflits Lombok/MapStruct
)
public abstract class ThirdPartyMapper {

    // Injection de Jackson pour convertir Map <-> JSON String
    @Autowired
    protected ObjectMapper objectMapper;

    // --- MAPPINGS ---

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    // Conversion manuelle de la Map vers String JSON
    @Mapping(target = "classification", expression = "java(mapToJson(request.classification()))")
    public abstract ThirdParty toEntity(ThirdPartyDTO.Request request);

    // Méthode simple (sans relations)
    public ThirdPartyDTO.Response toResponse(ThirdParty entity) {
        return toRichResponse(entity, Collections.emptyList(), Collections.emptyList());
    }

    // Méthode riche (avec relations)
    // Note: Pas besoin de @Mapping ici car on construit le record manuellement
    public ThirdPartyDTO.Response toRichResponse(ThirdParty entity, List<AddressDTO.Response> addresses, List<ContactDTO.Response> contacts) {
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

    // --- HELPER METHODS ---

    /**
     * Convertit une Map<String, Object> en String JSON pour la base de données.
     */
    protected String mapToJson(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            // En cas d'erreur, on log ou on renvoie null pour ne pas bloquer le flux
            return null;
        }
    }

    /**
     * Helper pour convertir les Tableaux (DB) en Listes (DTO)
     */
    protected List<String> map(String[] value) {
        return value == null ? List.of() : Arrays.asList(value);
    }

    /**
     * Helper pour convertir les Listes (DTO) en Tableaux (DB)
     */
    protected String[] map(List<String> value) {
        return value == null ? new String[0] : value.toArray(new String[0]);
    }
}