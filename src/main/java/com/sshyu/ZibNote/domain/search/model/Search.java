package com.sshyu.zibnote.domain.search.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class Search extends BaseFields {
    
    private final Long searchId;

    private final Member member;

    private final String title;

    private final String region;

    private final String description;

    public void assureOwner(Long memberId) {
        if (!this.member.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException();
        }
    }

}
