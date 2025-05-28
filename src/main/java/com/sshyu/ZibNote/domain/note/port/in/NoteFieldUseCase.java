package com.sshyu.zibnote.domain.note.port.in;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnote.domain.note.model.NoteField;

public interface NoteFieldUseCase {
    
    Long registerNoteField(NoteField noteField);

    List<NoteField> listNoteFieldsByMember(UUID memberId);

    void softDeleteNoteField(Long noteFieldId, UUID memberId);

}
