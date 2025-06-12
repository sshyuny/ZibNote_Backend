package com.sshyu.zibnote.domain.search.port.out;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.SearchStructure;

public interface SearchStructureRepository {
    
    UUID save(SearchStructure searchStructure);

    SearchStructure findBySearchStructureId(UUID searchStructureId);

    List<SearchStructure> findAllBySearchId(UUID searchId);

    void softDeleteBySearchStructureId(UUID searchStructureId);

    void updateBySearchStructure(SearchStructure searchStructure);

}
