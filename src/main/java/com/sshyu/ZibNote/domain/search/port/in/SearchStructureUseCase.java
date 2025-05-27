package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.SearchStructure;

public interface SearchStructureUseCase {
    
    UUID registerSearchStructure(SearchStructure searchStructure, Long loginedMemberId);
    
    List<SearchStructure> listSearchStructuresBySearch(Long searchId, Long loginedMemberId);

    void softDeleteSearchStructure(UUID searchStructureId, Long loginedMemberId);

    SearchStructure assertSearchStructureOwner(UUID searchStructureId, Long loginedMemberId);

}
