package com.sshyu.zibnote.fixture;

import java.util.UUID;

import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public class SearchStructureNoteFixture {

    public static final UUID NOTE_ID_OF_MEMBER_A_1 = UUID.randomUUID();
    public static final UUID NOTE_ID_OF_MEMBER_A_2 = UUID.randomUUID();

    public static SearchStructureNote createNote(final UUID searchStructureNoteId, final UUID searchStructureId, final Long noteFieldId) {
        return SearchStructureNote.builder()
            .searchStructureNoteId(searchStructureNoteId)
            .searchStructure(SearchStructure.onlyId(searchStructureId))
            .noteField(NoteField.onlyId(noteFieldId))
            .build();
    }

    public static SearchStructureNote validNote1OwnedByA() {
        return SearchStructureNote.ofBasic(
            NOTE_ID_OF_MEMBER_A_1, 
            SearchStructureFixture.validSearchStructure1OwnedByA(), 
            NoteFieldFixture.validNoteField1OwnedByA(),
            null,
            null,
            null
        );
    }
    public static SearchStructureNote validNote2OwnedByA() {
        return SearchStructureNote.ofBasic(
            NOTE_ID_OF_MEMBER_A_2, 
            SearchStructureFixture.validSearchStructure1OwnedByA(), 
            NoteFieldFixture.validNoteField1OwnedByA(),
            null,
            null,
            null
        );
    }

}
