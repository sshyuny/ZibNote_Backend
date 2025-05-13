package com.sshyu.zibnote.domain.member.model;

import com.sshyu.zibnote.domain.common.BaseFields;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Member extends BaseFields {
    
    private final Long memberId;

    private final String name;

    public static Member onlyId(final Long memberId) {
        return Member.builder().memberId(memberId).build();
    }
    
}
