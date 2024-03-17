package com.vicarius.apiquota.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;

import static com.vicarius.apiquota.constant.TableNameConstants.USERQUOTA;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = USERQUOTA)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserQuotaEntity extends BaseEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String userId;

    private Boolean blocked;
    private int remainQuota;

    @Version
    private Long version;

    public void incrementVersion() {
        this.version++;
    }
}
