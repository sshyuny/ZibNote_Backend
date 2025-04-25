package com.sshyu.zibnote.domain.search.port.out;

import java.util.List;

import com.sshyu.zibnote.domain.search.model.SearchStructure;

public interface SearchStructureRepository {
    
    Long save(SearchStructure searchStructure);

    SearchStructure findBySearchStructureId(Long searchStructureId);

    List<SearchStructure> findAllBySearchId(Long searchId);

    void softDeleteBySearchStructureId(Long searchStructureId);

}
