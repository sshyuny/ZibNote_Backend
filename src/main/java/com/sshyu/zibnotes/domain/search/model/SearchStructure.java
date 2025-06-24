package com.sshyu.zibnotes.domain.search.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sshyu.zibnotes.domain.common.BaseFields;
import com.sshyu.zibnotes.domain.search.exception.InvalidSearchStructureException;
import com.sshyu.zibnotes.domain.structure.model.Structure;

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
        if  (this.search == null || this.search.getSearchId() == null) {
            throw new InvalidSearchStructureException();
        }
        if  (this.structure == null || this.structure.getStructureId() == null) {
            throw new InvalidSearchStructureException();
        }
    }

    /**
     * 수정 전 유효성을 검사합니다.
     * 
     * Search는 수정 대상이 아니기 때문에 null이 들어가야 합니다.
     * Structure은 비어있어서는 안됩니다.
     */
    public void validateForUpdate() {
        if  (this.search != null) {
            throw new InvalidSearchStructureException();
        }
        if  (this.structure == null || this.structure.getStructureId() == null) {
            throw new InvalidSearchStructureException();
        }
    }

}
