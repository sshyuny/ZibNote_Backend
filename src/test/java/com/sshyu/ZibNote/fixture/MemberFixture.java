package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.member.model.Member;

public class MemberFixture {

    public static final Long MEMBER_A_ID = 1001L;
    public static final Long MEMBER_B_ID = 1002L;

    public static final String NAME_A = "sshyu";
    public static final String NAME_B = "sasha";

    public static Member ofMemberA() {
        return Member.builder()
            .memberId(MEMBER_A_ID)
            .name(NAME_A)
            .build();
    }

    public static Member of(final Long memberId, final String name) {
        return Member.builder()
            .memberId(memberId)
            .name(name)
            .build();
    }

    public static Member withoutId(final String name) {
        return Member.builder()
            .name(name)
            .build();
    }
    
}
