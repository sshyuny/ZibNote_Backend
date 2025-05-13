package com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLRestriction;

import com.sshyu.zibnote.adapter.out.persistence.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "STRUCTURE", uniqueConstraints = {
    @UniqueConstraint(name = "structure_number_address_unique",
        columnNames = {"numberAddress"}),
    @UniqueConstraint(name = "structure_road_address_unique",
        columnNames = {"roadAddress"})
})
@SQLRestriction("is_deleted = 0")
@Getter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class StructureEntity extends BaseEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long structureId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String numberAddress;

    @Column(length = 255)
    private String roadAddress;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private Integer builtYear;

    public static StructureEntity ref(final Long structureId) {
        return StructureEntity.builder()
                    .structureId(structureId)
                    .build();
    }

}
