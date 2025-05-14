package com.sshyu.zibnote.domain.note.port.in;

import java.util.List;

import com.sshyu.zibnote.domain.note.model.NoteField;

public interface NoteFieldUseCase {
    
    Long registerNoteField(NoteField noteField);

    List<NoteField> listNoteFieldsByMember(Long memberId);

    void softDeleteNoteField(Long noteFieldId, Long memberId);

}
