package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public class SearchStructureNoteFixture {

    public static final Long NOTE_ID_OF_MEMBER_A_1 = 6001L;
    public static final Long NOTE_ID_OF_MEMBER_A_2 = 6002L;

    public static SearchStructureNote ofNote1WithMemberA() {
        return SearchStructureNoteFixture.of(
            NOTE_ID_OF_MEMBER_A_1, SearchStructureFixture.ofSearchStructure1WithMemberA(), NoteFieldFixture.ofNoteFieldWithMemberA()
        );
    }
    public static SearchStructureNote ofNote2WithMemberA() {
        return SearchStructureNoteFixture.of(
            NOTE_ID_OF_MEMBER_A_2, SearchStructureFixture.ofSearchStructure1WithMemberA(), NoteFieldFixture.ofNoteFieldWithMemberA()
        );
    }

    public static SearchStructureNote ofValidNote() {
        return SearchStructureNoteFixture.of(
            null, SearchStructureFixture.ofSearchStructure1WithMemberA(), NoteFieldFixture.ofNoteFieldWithMemberA()
        );
    }
    public static SearchStructureNote ofInvalidNote() {
        return SearchStructureNoteFixture.of(
            null, SearchStructureFixture.ofSearchStructure1WithMemberA(), null
        );
    }
    
    public static SearchStructureNote of(final Long searchStructureNoteId, final SearchStructure searchStructure, final NoteField noteField) {
        return SearchStructureNote.builder()
            .searchStructureNoteId(searchStructureNoteId)
            .searchStructure(searchStructure)
            .noteField(noteField)
            .build();
    }
    public static SearchStructureNote withoutId(final Long searchStructureId, final Long noteFieldId) {
        return SearchStructureNote.builder()
            .searchStructure(SearchStructure.onlyId(searchStructureId))
            .noteField(NoteField.onlyId(noteFieldId))
            .build();
    }
    
}
