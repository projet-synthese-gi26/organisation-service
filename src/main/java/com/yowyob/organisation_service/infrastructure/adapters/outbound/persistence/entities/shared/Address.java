package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("address")
public class Address extends BaseEntity {

    @Column("addressable_id")
    private UUID addressableId;

    @Column("addressable_type")
    private String addressableType; // "ORGANIZATION", "AGENCY"

    private String type;

    @Column("address_line_1")
    private String addressLine1;

    @Column("address_line_2")
    private String addressLine2;

    private String city;
    private String state;
    private String locality;

    @Column("zip_code")
    private String zipCode;

    @Column("country_id")
    private UUID countryId;

    @Column("po_box")
    private String poBox;

    @Column("neighbor_hood")
    private String neighborHood; // Quartier

    @Column("informal_description")
    private String informalDescription;

    @Column("is_default")
    private Boolean isDefault;

    private Double latitude;
    private Double longitude;
}