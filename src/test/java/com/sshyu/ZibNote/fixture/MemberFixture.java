package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.member.model.Member;

public class MemberFixture {

    public static final Long MEMBER_A_ID = 1001L;
    public static final Long MEMBER_B_ID = 1002L;

    public static final String MEMBER_A_NAME = "sshyu";
    public static final String MEMBER_B_NAME = "sasha";

    public static Member validMemberA() {
        return Member.builder()
            .memberId(MEMBER_A_ID)
            .name(MEMBER_A_NAME)
            .build();
    }

}
