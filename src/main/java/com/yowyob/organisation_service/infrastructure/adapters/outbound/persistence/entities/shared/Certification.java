package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("certification")
public class Certification extends BaseEntity {
    @Column("organization_id")
    private UUID organizationId;
    private String type;
    private String name;
    private String description;
    @Column("obtainement_date")
    private LocalDateTime obtainementDate;
}