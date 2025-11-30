package com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.shared;



import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.base_entity.BaseEntity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("proposed_activity")
public class ProposedActivity extends BaseEntity {
    @Column("organization_id")
    private UUID organizationId;
    private String type;
    private String name;
    private BigDecimal rate;
    private String description;
}