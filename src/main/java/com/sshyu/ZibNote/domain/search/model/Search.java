package com.sshyu.zibnote.domain.search.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Search extends BaseFields {
    
    private final UUID searchId;

    private final Member member;

    private final String title;

    private final String region;

    private final String description;

    public static Search ofFull(final UUID searchId, final Member member, final String title, final String region, final String description, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return Search.builder()
            .searchId(searchId)
            .member(member)
            .title(title)
            .region(region)
            .description(description)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static Search ofBasic(final UUID searchId, final Member member, final String title, final String region, final String description) {
        return Search.builder()
            .searchId(searchId)
            .member(member)
            .title(title)
            .region(region)
            .description(description)
            .build();
    }

    public static Search onlyId(final UUID searchId) {
        return Search.builder()
                    .searchId(searchId)
                    .build();
    }

    public void assureOwner(final UUID memberId) {
        if (!this.member.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("존재하지 않거나 접근할 수 없는 데이터입니다.");
        }
    }

}
