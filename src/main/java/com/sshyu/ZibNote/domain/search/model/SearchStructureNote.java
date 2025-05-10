package com.sshyu.zibnote.domain.search.model;

import com.sshyu.zibnote.domain.common.BaseFields;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.exception.NotValidSearchStructureNoteException;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Getter
public class SearchStructureNote extends BaseFields {
    
    private Long searchStructureNoteId;

    private SearchStructure searchStructure;

    private NoteField noteField;

    private EvalType evalType;

    private String evalValue;

    private String note;

    public void validateForRegister() {
        if (searchStructure == null || searchStructure.getSearchStructureId() == null) {
            throw new NotValidSearchStructureNoteException();
        }
        if (noteField == null || noteField.getNoteFieldId() == null) {
            throw new NotValidSearchStructureNoteException();
        }
    }
    
}
