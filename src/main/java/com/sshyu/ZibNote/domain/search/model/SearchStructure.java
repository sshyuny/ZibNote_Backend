package com.sshyu.zibnote.domain.search.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.search.exception.InvalidSearchStructureException;
import com.sshyu.zibnote.domain.structure.model.Structure;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class SearchStructure extends BaseFields {
    
    private final Long searchStructureId;

    private final Search search;

    private final Structure structure;

    private final String description;

    public static SearchStructure onlyId(final Long searchStructureId) {
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
