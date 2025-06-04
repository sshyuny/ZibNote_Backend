package com.sshyu.zibnote.domain.search.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.common.exception.AlreadyDeletedException;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.exception.InvalidSearchStructureNoteException;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNotFoundException;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class SearchStructureNote extends BaseFields {
    
    private final UUID searchStructureNoteId;

    private final SearchStructure searchStructure;

    private final NoteField noteField;

    private final EvalType evalType;

    private final String evalValue;

    private final String note;

    public static SearchStructureNote ofFull(final UUID searchStructureNoteId, final SearchStructure searchStructure, 
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

    public static SearchStructureNote ofBasic(final UUID searchStructureNoteId, final SearchStructure searchStructure, 
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

    public void validateForUpdate() {
        if (searchStructureNoteId == null) {
            throw new SearchStructureNotFoundException("ID에 NULL 값");
        }
        if (isDeleted == 1) {
            throw new AlreadyDeletedException("이미 삭제된 데이터");
        }
        if (searchStructure == null || searchStructure.getSearchStructureId() == null) {
            throw new InvalidSearchStructureNoteException("필수 항목인 SearchStructure 값 누락");
        }
        if (noteField == null || noteField.getNoteFieldId() == null) {
            throw new InvalidSearchStructureNoteException("필수 항목인 NoteField 값 누락");
        }
    }

}
