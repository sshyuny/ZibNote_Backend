package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.model.Search;

public class SearchFixture {

    public static final Long SEARCH_ID_OF_MEMBER_A = 4001L;
    public static final String TITLE = "산본역 2025 임장";
    public static final String REGION = "경기 군포시";

    public static Search ofSearchWithMemberA() {
        return Search.builder()
            .searchId(SEARCH_ID_OF_MEMBER_A)
            .member(MemberFixture.ofMemberA())
            .title(TITLE)
            .region(REGION)
            .build();
    }
    
    public static Search withoutId(final Long memberId, final String title, final String region) {
        return Search.builder()
            .member(Member.onlyId(memberId))
            .title(title)
            .region(region)
            .build();
    }
    
}
