package com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;

import com.sshyu.zibnotes.adapter.out.persistence.common.BaseEntity;
import com.sshyu.zibnotes.adapter.out.persistence.structure.jpa.entity.StructureEntity;
import com.sshyu.zibnotes.domain.search.model.SearchStructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    
    @Id @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID searchStructureId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "search_id", nullable = false)
    private SearchEntity searchEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "structure_id", nullable = false)
    private StructureEntity structureEntity;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    public static SearchStructureEntity ref(final UUID searchStructureId) {
        return SearchStructureEntity.builder()
                    .searchStructureId(searchStructureId)
                    .build();
    }

    /**
     * SearchStructureEntity 필드 내용을 변경합니다.
     * searchStructureId, searchEntity 필드는 변경되지 않습니다.
     */
    public void update(final SearchStructure domain) {
        this.structureEntity = StructureEntity.ref(domain.getStructure().getStructureId());
        this.description = domain.getDescription();
    }

}
