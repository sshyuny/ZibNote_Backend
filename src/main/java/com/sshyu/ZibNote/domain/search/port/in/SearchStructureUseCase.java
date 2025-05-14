package com.sshyu.zibnote.domain.search.port.in;

import java.util.List;

import com.sshyu.zibnote.domain.search.model.SearchStructure;

public interface SearchStructureUseCase {
    
    Long registerSearchStructure(SearchStructure searchStructure, Long loginedMemberId);
    
    List<SearchStructure> listSearchStructuresBySearch(Long searchId, Long loginedMemberId);

    void softDeleteSearchStructure(Long searchStructureId, Long loginedMemberId);

    SearchStructure assertSearchStructureOwner(Long searchStructureId, Long loginedMemberId);

}
