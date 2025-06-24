package com.sshyu.zibnote.fixture;

import java.util.UUID;

import com.sshyu.zibnote.domain.member.model.Member;

public class MemberFixture {

    public static final UUID MEMBER_A_ID = UUID.randomUUID();
    public static final UUID MEMBER_B_ID = UUID.randomUUID();

    public static final String MEMBER_A_NAME = "sshyu";
    public static final String MEMBER_B_NAME = "sasha";

    public static Member validMemberA() {
        return Member.builder()
            .memberId(MEMBER_A_ID)
            .name(MEMBER_A_NAME)
            .build();
    }

}
