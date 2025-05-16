package com.sshyu.zibnote.domain.note.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class NoteField extends BaseFields {
    
    private final Long noteFieldId;

    private final Member member;

    private final String name;

    private final String description;

    public static NoteField onlyId(final Long noteFieldId) {
        return NoteField.builder()
                    .noteFieldId(noteFieldId)
                    .build();
    }

    public void assureOwner(final Long memberId) {
        if (!this.member.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException();
        }
    }
    
}
