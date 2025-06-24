package com.sshyu.zibnotes.domain.search;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.sshyu.zibnotes.domain.note.model.NoteField;
import com.sshyu.zibnotes.domain.search.exception.InvalidSearchStructureNoteException;
import com.sshyu.zibnotes.domain.search.model.EvalType;
import com.sshyu.zibnotes.domain.search.model.SearchStructure;
import com.sshyu.zibnotes.domain.search.model.SearchStructureNote;

public class SearchStructureNoteTest {

    static final UUID SAMPLE_SEARCH_STRUCTURE_NOTE_ID = UUID.randomUUID();
    static final SearchStructure SAMPLE_SEARCH_STRUCTURE = SearchStructure.onlyId(UUID.randomUUID());
    static final NoteField SAMPLE_NOTE_FIELD = NoteField.onlyId(1L);
    
    @Test
    void ensureEvalTypeAndValueMatch_EvalType과_EvalValue_모두_null로_정상_상황() {

        SearchStructureNote note = SearchStructureNote.ofBasic(
            SAMPLE_SEARCH_STRUCTURE_NOTE_ID, SAMPLE_SEARCH_STRUCTURE, SAMPLE_NOTE_FIELD, null, null, null);

        assertDoesNotThrow(() -> note.ensureEvalTypeAndValueMatch());
    }

    @Test
    void ensureEvalTypeAndValueMatch_EvalType_STAR에_정상_EvalValue() {

        SearchStructureNote note = SearchStructureNote.ofBasic(
            SAMPLE_SEARCH_STRUCTURE_NOTE_ID, SAMPLE_SEARCH_STRUCTURE, SAMPLE_NOTE_FIELD, EvalType.STAR, "5", null);

        assertDoesNotThrow(() -> note.ensureEvalTypeAndValueMatch());
    }

    @Test
    void ensureEvalTypeAndValueMatch_EvalType_STAR에_잘못된_EvalValue로_예외_발생() {

        SearchStructureNote note = SearchStructureNote.ofBasic(
            SAMPLE_SEARCH_STRUCTURE_NOTE_ID, SAMPLE_SEARCH_STRUCTURE, SAMPLE_NOTE_FIELD, EvalType.STAR, "7", null);

        assertThrows(InvalidSearchStructureNoteException.class, () -> 
            note.ensureEvalTypeAndValueMatch());
    }

    @Test
    void ensureEvalTypeAndValueMatch_Eval_Type_SCORE에_정상_EvalValue() {

        SearchStructureNote note = SearchStructureNote.ofBasic(
            SAMPLE_SEARCH_STRUCTURE_NOTE_ID, SAMPLE_SEARCH_STRUCTURE, SAMPLE_NOTE_FIELD, EvalType.SCORE, "100", null);

        assertDoesNotThrow(() -> note.ensureEvalTypeAndValueMatch());
    }

    @Test
    void ensureEvalTypeAndValueMatch_Eval_Type_SCORE에_잘못된_EvalValue로_예외_발생() {

        SearchStructureNote note = SearchStructureNote.ofBasic(
            SAMPLE_SEARCH_STRUCTURE_NOTE_ID, SAMPLE_SEARCH_STRUCTURE, SAMPLE_NOTE_FIELD, EvalType.SCORE, "101", null);

        assertThrows(InvalidSearchStructureNoteException.class, () -> 
            note.ensureEvalTypeAndValueMatch());
    }

}
