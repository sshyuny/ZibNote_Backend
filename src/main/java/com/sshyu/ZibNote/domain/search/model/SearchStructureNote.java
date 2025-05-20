package com.sshyu.zibnote.domain.search.model;

import java.time.LocalDateTime;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.exception.InvalidSearchStructureNoteException;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class SearchStructureNote extends BaseFields {
    
    private final Long searchStructureNoteId;

    private final SearchStructure searchStructure;

    private final NoteField noteField;

    private final EvalType evalType;

    private final String evalValue;

    private final String note;

    public static SearchStructureNote ofFull(final Long searchStructureNoteId, final SearchStructure searchStructure, 
            final NoteField noteField, final EvalType evalType, final String evalValue, final String note, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return SearchStructureNote.builder()
            .searchStructureNoteId(searchStructureNoteId)
            .searchStructure(searchStructure)
            .noteField(noteField)
            .evalType(evalType)
            .evalValue(evalValue)
            .note(note)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static SearchStructureNote ofBasic(final Long searchStructureNoteId, final SearchStructure searchStructure, 
            final NoteField noteField, final EvalType evalType, final String evalValue, final String note) {
        return SearchStructureNote.builder()
            .searchStructureNoteId(searchStructureNoteId)
            .searchStructure(searchStructure)
            .noteField(noteField)
            .evalType(evalType)
            .evalValue(evalValue)
            .note(note)
            .build();
    }

    public void validateForRegister() {
        if (searchStructure == null || searchStructure.getSearchStructureId() == null) {
            throw new InvalidSearchStructureNoteException();
        }
        if (noteField == null || noteField.getNoteFieldId() == null) {
            throw new InvalidSearchStructureNoteException();
        }
    }

}
