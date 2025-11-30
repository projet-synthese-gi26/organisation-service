package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.reference;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("business_domain")
public class BusinessDomain extends BaseEntity {

    private String code;

    private String service;

    @Column("parent_id")
    private UUID parentId;

    private String name;

    @Column("image_uri")
    private String imageUri;

    @Column("image_id")
    private UUID imageId;

    private String type;

    @Column("type_label")
    private String typeLabel;

    private String description;
}