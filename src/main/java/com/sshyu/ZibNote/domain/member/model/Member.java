package com.sshyu.zibnote.domain.member.model;

import java.time.LocalDateTime;

import com.sshyu.zibnote.domain.common.BaseFields;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Member extends BaseFields {
    
    private final Long memberId;

    private final String name;

    public static Member ofFull(final Long memberId, final String name, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return Member.builder()
            .memberId(memberId)
            .name(name)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static Member ofBasic(final Long memberId, final String name) {
        return Member.builder()
            .memberId(memberId)
            .name(name)
            .build();
    }

    public static Member onlyId(final Long memberId) {
        return Member.builder()
            .memberId(memberId)
            .build();
    }

}
