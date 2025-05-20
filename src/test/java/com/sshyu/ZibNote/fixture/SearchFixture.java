package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.search.model.Search;

public class SearchFixture {

    public static final Long SEARCH_1_ID = 4001L;
    public static final Long SEARCH_2_ID = 4002L;

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
