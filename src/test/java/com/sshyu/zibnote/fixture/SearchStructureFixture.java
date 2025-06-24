package com.sshyu.zibnote.fixture;

import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.SearchStructure;

public class SearchStructureFixture {

    public static final UUID SEARCH_STRUCTURE_1 = UUID.randomUUID();
    public static final UUID SEARCH_STRUCTURE_2_ID = UUID.randomUUID();

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
