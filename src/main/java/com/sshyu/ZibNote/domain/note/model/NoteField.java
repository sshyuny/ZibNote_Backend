package com.sshyu.zibnote.domain.note.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class NoteField extends BaseFields {
    
    private final Long noteFieldId;

    private final Member member;

    private final String name;

    private final String description;

    /*
     * 최초 생성 이전에 호출
     */
    public void prepareForCreation() {
        super.fillBaseFields();
    }

    public void assureOwner(Long memberId) {
        if (this.member.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException();
        }
    }
    
}
