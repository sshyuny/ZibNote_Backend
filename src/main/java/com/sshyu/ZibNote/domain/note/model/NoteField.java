package com.sshyu.zibnote.domain.note.model;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public static NoteField ofFull(final Long noteFieldId, final Member member, final String name, final String description, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return NoteField.builder()
            .noteFieldId(noteFieldId)
            .member(member)
            .name(name)
            .description(description)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static NoteField ofBasic(final Long noteFieldId, final Member member, final String name, final String description) {
        return NoteField.builder()
            .noteFieldId(noteFieldId)
            .member(member)
            .name(name)
            .description(description)
            .build();
    }

    public static NoteField onlyId(final Long noteFieldId) {
        return NoteField.builder()
            .noteFieldId(noteFieldId)
            .build();
    }

    public void assureOwner(final UUID memberId) {
        if (!this.member.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException();
        }
    }

}
