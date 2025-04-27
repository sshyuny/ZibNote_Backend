package com.sshyu.zibnote.domain.search.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.search.exception.NotValidSearchStructureException;
import com.sshyu.zibnote.domain.structure.model.Structure;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class SearchStructure extends BaseFields {
    
    private Long searchStructureId;

    private Search search;

    private Structure structure;

    private String description;

    public void validate() {
        if  (this.search.getSearchId() == null || this.structure.getStructureId() == null) {
            throw new NotValidSearchStructureException();
        }
    }

}
