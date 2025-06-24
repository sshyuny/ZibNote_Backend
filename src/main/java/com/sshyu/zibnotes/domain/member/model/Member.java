package com.sshyu.zibnotes.domain.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sshyu.zibnotes.domain.common.BaseFields;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Member extends BaseFields {
    
    private final UUID memberId;

    private final String name;

    public static Member ofFull(final UUID memberId, final String name, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return Member.builder()
            .memberId(memberId)
            .name(name)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static Member ofBasic(final UUID memberId, final String name) {
        return Member.builder()
            .memberId(memberId)
            .name(name)
            .build();
    }

    public static Member onlyId(final UUID memberId) {
        return Member.builder()
            .memberId(memberId)
            .build();
    }

}
