package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.model.Search;

public class SearchFixture {

    public static final Long SEARCH_ID_OF_MEMBER_A_1 = 4001L;
    public static final Long SEARCH_ID_OF_MEMBER_A_2 = 4002L;

    public static final String TITLE_1 = "산본역 2025 임장";
    public static final String REGION_1 = "경기 군포시";
    public static final String TITLE_2 = "병점역 2025 임장";
    public static final String REGION_2 = "경기 화성시";

    public static Search ofSearch1WithMemberA() {
        return Search.builder()
            .searchId(SEARCH_ID_OF_MEMBER_A_1)
            .member(MemberFixture.ofMemberA())
            .title(TITLE_1)
            .region(REGION_1)
            .build();
    }
    public static Search ofSearch2WithMemberA() {
        return Search.builder()
            .searchId(SEARCH_ID_OF_MEMBER_A_2)
            .member(MemberFixture.ofMemberA())
            .title(TITLE_1)
            .region(REGION_1)
            .build();
    }
    
    public static Search of(final Long searchId, final Long memberId, final String title, final String region) {
        return Search.builder()
            .searchId(searchId)
            .member(Member.onlyId(memberId))
            .title(title)
            .region(region)
            .build();
    }

}
