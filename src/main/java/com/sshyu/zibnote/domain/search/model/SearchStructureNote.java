package com.sshyu.zibnote.domain.search.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sshyu.zibnote.domain.common.BaseFields;
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

    /**
     * SearchStructureNote 등록 전 유효성 검사
     */
    public void validateForRegister() {
        if (searchStructure == null || searchStructure.getSearchStructureId() == null) {
            throw new InvalidSearchStructureNoteException();
        }
        if (noteField == null || noteField.getNoteFieldId() == null) {
            throw new InvalidSearchStructureNoteException();
        }
        ensureEvalTypeAndValueMatch();
    }

    /**
     * SearchStructureNote 수정 전 유효성 검사
     */
    public void validateForUpdate(SearchStructureNote selectedNote) {
        if (this.searchStructureNoteId == null) {
            throw new SearchStructureNotFoundException("ID에 NULL 값 불가");
        }
        if (!this.searchStructureNoteId.equals(selectedNote.getSearchStructureNoteId())) {
            throw new InvalidSearchStructureNoteException("ID는 변경 불가");
        }
        if (!selectedNote.getSearchStructure().getSearchStructureId().equals(selectedNote.getSearchStructure().getSearchStructureId())) {
            throw new InvalidSearchStructureNoteException("SearchStructure는 변경 불가");
        }
        if (this.noteField == null || this.noteField.getNoteFieldId() == null) {
            throw new InvalidSearchStructureNoteException("필수 항목인 NoteField 값 누락");
        }
        ensureEvalTypeAndValueMatch();
    }

    /**
     * EvalType에 적절한 EvalValue 값이 들어있는지 검증
     */
    public void ensureEvalTypeAndValueMatch() {
        try {
            if (evalType == null) {
                if (evalValue != null) throw new InvalidSearchStructureNoteException("EvalType 없는데 EvalValue 값 있음");
            } else {
                if (!evalType.hasProperValue(evalValue)) {
                    throw new InvalidSearchStructureNoteException("EvalType에 부적절한 EvalValue 값");
                }
            }
        } catch (NumberFormatException ex) {
            throw new InvalidSearchStructureNoteException("EvalValue 형식 오류");
        }
    }

}
