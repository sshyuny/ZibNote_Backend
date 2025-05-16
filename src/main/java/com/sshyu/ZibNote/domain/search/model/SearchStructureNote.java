package com.sshyu.zibnote.domain.search.model;

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

    public void validateForRegister() {
        if (searchStructure == null || searchStructure.getSearchStructureId() == null) {
            throw new InvalidSearchStructureNoteException();
        }
        if (noteField == null || noteField.getNoteFieldId() == null) {
            throw new InvalidSearchStructureNoteException();
        }
    }
    
}
