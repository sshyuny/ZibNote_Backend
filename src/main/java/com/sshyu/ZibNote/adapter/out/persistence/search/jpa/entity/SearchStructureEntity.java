package com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity;

import org.hibernate.annotations.SQLRestriction;

import com.sshyu.zibnote.adapter.out.persistence.common.BaseEntity;
import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity.StructureEntity;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "SEARCH_STRUCTURE")
@SQLRestriction("is_deleted = 0")
@Getter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class SearchStructureEntity extends BaseEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchStructureId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "search_id", nullable = false)
    private SearchEntity searchEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "structure_id", nullable = false)
    private StructureEntity structureEntity;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    public static SearchStructureEntity ref(Long searchStructureId) {
        return SearchStructureEntity.builder()
                    .searchStructureId(searchStructureId)
                    .build();
    }

}
