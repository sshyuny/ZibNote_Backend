package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public class SearchStructureNoteFixture {

    public static final Long NOTE_ID_OF_MEMBER_A_1 = 6001L;
    public static final Long NOTE_ID_OF_MEMBER_A_2 = 6002L;

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

    public static SearchStructureNote validNoteWithoutId() {
        return SearchStructureNote.ofBasic(
            null,
            SearchStructureFixture.validSearchStructure1OwnedByA(), 
            NoteFieldFixture.validNoteField1OwnedByA(),
            null,
            null,
            null
        );
    }
    public static SearchStructureNote invalidNote() {
        return SearchStructureNote.ofBasic(
            null,
            SearchStructureFixture.validSearchStructure1OwnedByA(), 
            NoteField.onlyId(null),
            null,
            null,
            null
        );
    }

    public static SearchStructureNote createNote(final Long searchStructureNoteId, final Long searchStructureId, final Long noteFieldId) {
        return SearchStructureNote.builder()
            .searchStructureNoteId(searchStructureNoteId)
            .searchStructure(SearchStructure.onlyId(searchStructureId))
            .noteField(NoteField.onlyId(noteFieldId))
            .build();
    }

}
