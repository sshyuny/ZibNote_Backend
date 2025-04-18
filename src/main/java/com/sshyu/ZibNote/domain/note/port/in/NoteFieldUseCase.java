package com.sshyu.zibnote.domain.note.port.in;

import java.util.List;

import com.sshyu.zibnote.domain.note.model.NoteField;

public interface NoteFieldUseCase {
    
    void save(NoteField noteField);

    List<NoteField> findByMember(Long memberId);

}
