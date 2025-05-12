package com.sshyu.zibnote.domain.note.port.out;

import java.util.List;

import com.sshyu.zibnote.domain.note.model.NoteField;

public interface NoteFieldRepository {
    
    Long save(NoteField noteField);

    NoteField findByNoteFieldId(Long noteFieldId);
    
    NoteField findByMemberAndName(Long memberId, String name);

    List<NoteField> findAllByMemberId(Long memberId);

    void softDeleteByNoteFieldId(Long noteFieldId);
    
}
