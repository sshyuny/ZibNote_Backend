package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.structure.model.Structure;

public class SearchStructureFixture {

    public static final Long SEARCH_STRUCTURE_1_ID_OF_MEMBER_A = 5001L;
    public static final Long SEARCH_STRUCTURE_2_ID_OF_MEMBER_A = 5002L;

    public static SearchStructure ofSearchStructure1WithMemberA() {
        return SearchStructure.builder()
            .searchStructureId(SEARCH_STRUCTURE_1_ID_OF_MEMBER_A)
            .search(SearchFixture.ofSearch1WithMemberA())
            .structure(StructureFixture.ofStructureSolApt())
            .build();
    }
    public static SearchStructure ofSearchStructure2WithMemberA() {
        return SearchStructure.builder()
            .searchStructureId(SEARCH_STRUCTURE_2_ID_OF_MEMBER_A)
            .search(SearchFixture.ofSearch1WithMemberA())
            .structure(StructureFixture.ofStructureSolApt())
            .build();
    }
    
    public static SearchStructure withoutId(final Long searchId, final Long structureId) {
        return SearchStructure.builder()
            .search(Search.onlyId(searchId))
            .structure(Structure.onlyId(structureId))
            .build();
    }

    public static SearchStructure of(final Long searchStructureId, final Long searchId, final Long structureId) {
        return SearchStructure.builder()
            .searchStructureId(searchStructureId)
            .search(Search.onlyId(searchId))
            .structure(Structure.onlyId(structureId))
            .build();
    }
    
}
