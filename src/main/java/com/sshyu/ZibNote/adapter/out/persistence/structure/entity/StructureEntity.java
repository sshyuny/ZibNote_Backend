package com.sshyu.zibnote.adapter.out.persistence.structure.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STRUCTURE")
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class StructureEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long structureId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private Integer builtYear;

}
