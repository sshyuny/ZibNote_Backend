package com.sshyu.zibnotes.domain.note.port.out;

import java.util.List;
import java.util.UUID;

import com.sshyu.zibnotes.domain.note.model.NoteField;

public interface NoteFieldRepository {
    
    Long save(NoteField noteField);

    NoteField findByNoteFieldId(Long noteFieldId);
    
    NoteField findByMemberAndName(UUID memberId, String name);

    List<NoteField> findAllByMemberId(UUID memberId);

    void softDeleteByNoteFieldId(Long noteFieldId);
    
}
