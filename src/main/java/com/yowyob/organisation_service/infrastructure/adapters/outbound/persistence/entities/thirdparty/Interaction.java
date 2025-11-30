package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.thirdparty;

import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("interaction")
public class Interaction extends BaseEntity {

    @Column("interaction_id")
    private UUID interactionId;

    @Column("prospect_id")
    private UUID prospectId;

    @Column("interaction_date")
    private LocalDateTime interactionDate;

    private String notes;
}