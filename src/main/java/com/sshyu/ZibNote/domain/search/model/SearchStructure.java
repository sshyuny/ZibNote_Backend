package com.sshyu.zibnote.domain.search.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.search.exception.InvalidSearchStructureException;
import com.sshyu.zibnote.domain.structure.model.Structure;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class SearchStructure extends BaseFields {
    
    private final UUID searchStructureId;

    private final Search search;

    private final Structure structure;

    private final String description;

    public static SearchStructure ofFull(final UUID searchStructureId, final Search search, final Structure structure, final String description, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return SearchStructure.builder()
            .searchStructureId(searchStructureId)
            .search(search)
            .structure(structure)
            .description(description)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static SearchStructure ofBasic(final UUID searchStructureId, final Search search, final Structure structure, final String description) {
        return SearchStructure.builder()
            .searchStructureId(searchStructureId)
            .search(search)
            .structure(structure)
            .description(description)
            .build();
    }

    public static SearchStructure onlyId(final UUID searchStructureId) {
        return SearchStructure.builder()
                    .searchStructureId(searchStructureId)
                    .build();
    }

    public void validate() {
        if  (this.search.getSearchId() == null || this.structure.getStructureId() == null) {
            throw new InvalidSearchStructureException();
        }
    }

}
