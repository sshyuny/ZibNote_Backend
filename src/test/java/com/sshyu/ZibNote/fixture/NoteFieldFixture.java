package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.note.model.NoteField;

public class NoteFieldFixture {

    public static final Long NOTE_FIELD_1_ID = 2001L;
    public static final Long NOTE_FIELD_2_ID = 2001L;

    public static final String NOTE_FIELD_1_NAME = "놀이터";
    public static final String NOTE_FILED_2_NAME = "상권과 거리";
    
    public static NoteField validNoteField1OwnedByA() {
        return NoteField.builder()
            .noteFieldId(NOTE_FIELD_1_ID)
            .member(MemberFixture.validMemberA())
            .name(NOTE_FIELD_1_NAME)
            .build();
    }
    public static NoteField validNoteField2OwnedByA() {
        return NoteField.builder()
            .noteFieldId(NOTE_FIELD_2_ID)
            .member(MemberFixture.validMemberA())
            .name(NOTE_FILED_2_NAME)
            .build();
    }

}
