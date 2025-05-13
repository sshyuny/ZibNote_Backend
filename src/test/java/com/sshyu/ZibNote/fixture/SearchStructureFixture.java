package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.structure.model.Structure;

public class SearchStructureFixture {

    public static final Long SEARCH_STRUCTURE_ID_OF_MEMBER_A = 5001L;

    public static SearchStructure ofSearchStructureWithMemberA() {
        return SearchStructure.builder()
            .searchStructureId(SEARCH_STRUCTURE_ID_OF_MEMBER_A)
            .search(SearchFixture.ofSearchWithMemberA())
            .structure(StructureFixture.ofStructureSolApt())
            .build();
    }
    
    public static SearchStructure withoutId(final Long searchId, final Long structureId) {
        return SearchStructure.builder()
            .search(Search.onlyId(searchId))
            .structure(Structure.onlyId(structureId))
            .build();
    }
    
}
