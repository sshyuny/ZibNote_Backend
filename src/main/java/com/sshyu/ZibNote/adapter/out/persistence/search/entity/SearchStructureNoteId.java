package com.sshyu.zibnote.adapter.out.persistence.search.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class SearchStructureNoteId implements Serializable {
    
    private Long searchStructureEntity;
    private Long noteFieldEntity;

}
