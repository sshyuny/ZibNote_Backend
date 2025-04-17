package com.sshyu.zibnote.domain.note.port.out;

import java.time.LocalDateTime;

import com.sshyu.zibnote.domain.note.model.NoteField;

public interface NoteFieldRepository {
    
    void save(NoteField noteField);

    NoteField findByNoteFieldId(Long noteFieldId);
    
    NoteField findByMemberAndName(Long memberId, String name);

    void softDeleteByNoteFieldId(Long noteFieldId, LocalDateTime updatedAt);
    
}
