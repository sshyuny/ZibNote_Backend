package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.SearchStructure;

public interface SearchStructureUseCase {
    
    UUID registerSearchStructure(SearchStructure searchStructure, UUID loginedMemberId);
    
    List<SearchStructure> listSearchStructuresBySearch(UUID searchId, UUID loginedMemberId);

    void softDeleteSearchStructure(UUID searchStructureId, UUID loginedMemberId);
    
    void updateSearchStructure(SearchStructure searchStructure, UUID loginedMemberId);

    SearchStructure assertSearchStructureOwner(UUID searchStructureId, UUID loginedMemberId);

}
