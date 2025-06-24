package com.sshyu.zibnotes.fixture;

import java.util.UUID;

import com.sshyu.zibnotes.domain.search.model.Search;

public class SearchFixture {

    public static final UUID SEARCH_1_ID = UUID.randomUUID();
    public static final UUID SEARCH_2_ID = UUID.randomUUID();

    public static final String SEARCH_1_TITLE = "산본역 2025 임장";
    public static final String SEARCH_1_REGION = "경기 군포시";
    public static final String SEARCH_2_TITLE = "병점역 2025 임장";
    public static final String SEARCH_2_REGION = "경기 화성시";

    public static Search validSearch1OwnedByA() {
        return Search.builder()
            .searchId(SEARCH_1_ID)
            .member(MemberFixture.validMemberA())
            .title(SEARCH_1_TITLE)
            .region(SEARCH_1_REGION)
            .build();
    }
    public static Search validSearch2OwnedByA() {
        return Search.builder()
            .searchId(SEARCH_2_ID)
            .member(MemberFixture.validMemberA())
            .title(SEARCH_1_TITLE)
            .region(SEARCH_1_REGION)
            .build();
    }

}
