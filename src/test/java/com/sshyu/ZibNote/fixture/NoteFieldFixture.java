package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;

public class NoteFieldFixture {

    public static final Long NOTE_FIELD_OF_MEMBER_A = 2001L;
    public static final String NAME_1 = "놀이터";
    public static final String NAME_2 = "상권과 거리";
    
    public static NoteField ofNoteFieldWithMemberA() {
        return NoteField.builder()
            .noteFieldId(NOTE_FIELD_OF_MEMBER_A)
            .member(MemberFixture.ofMemberA())
            .name(NAME_1)
            .build();
    }

    public static NoteField of(final Long noteFieldId, final Long memberId, final String name) {
        return NoteField.builder()
            .noteFieldId(noteFieldId)
            .member(Member.onlyId(memberId))
            .name(name)
            .build();
    }

}
