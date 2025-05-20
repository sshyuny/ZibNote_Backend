package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.search.model.SearchStructure;

public class SearchStructureFixture {

    public static final Long SEARCH_STRUCTURE_1 = 5001L;
    public static final Long SEARCH_STRUCTURE_2_ID = 5002L;

    public static SearchStructure validSearchStructure1OwnedByA() {
        return SearchStructure.builder()
            .searchStructureId(SEARCH_STRUCTURE_1)
            .search(SearchFixture.validSearch1OwnedByA())
            .structure(StructureFixture.validStructure1())
            .build();
    }
    public static SearchStructure validSearchStructure2OwnedByA() {
        return SearchStructure.builder()
            .searchStructureId(SEARCH_STRUCTURE_2_ID)
            .search(SearchFixture.validSearch1OwnedByA())
            .structure(StructureFixture.validStructure1())
            .build();
    }

}
