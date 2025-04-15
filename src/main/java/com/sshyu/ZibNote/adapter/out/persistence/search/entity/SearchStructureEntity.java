package com.sshyu.zibnote.adapter.out.persistence.search.entity;

import com.sshyu.zibnote.adapter.out.persistence.structure.entity.StructureEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SEARCH_STRUCTURE", uniqueConstraints = {@UniqueConstraint(
    name = "search_structure_unique",
    columnNames = {"search_id", "structure_id"}
)})
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class SearchStructureEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchStructureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_id")
    private SearchEntity searchEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private StructureEntity structureEntity;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

}
