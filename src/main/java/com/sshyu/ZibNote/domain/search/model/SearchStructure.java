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
        if  (this.search == null || this.search.getSearchId() == null) {
            throw new InvalidSearchStructureException();
        }
        if  (this.structure == null || this.structure.getStructureId() == null) {
            throw new InvalidSearchStructureException();
        }
    }

    /**
     * DB에서 조회된 SearchStructre과 비교하여, 수정하기에 적절한 필드로 구성되어있는지 확인합니다.
     * 
     * ID와 Search가 동일해야 합니다.
     * 
     * @param dbDomain DB에서 조회된 SearchStructure
     * @return 확인 결과
     */
    public boolean isUpdatableComparedTo(SearchStructure dbDomain) {
        if (!this.searchStructureId.equals(dbDomain.getSearchStructureId())) {return false;}
        if (!this.search.getSearchId().equals(dbDomain.getSearch().getSearchId())) {return false;}

        return true;
    }

}
