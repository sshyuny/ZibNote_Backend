package com.sshyu.zibnote.application.service.note;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.note.port.in.NoteFieldUseCase;
import com.sshyu.zibnote.domain.note.port.out.NoteFieldRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoteFieldService implements NoteFieldUseCase {

    private final NoteFieldRepository noteFieldRepository;

    @Override
    public void save(NoteField noteField) {
        noteFieldRepository.save(noteField);
    }

    @Override
    public List<NoteField> findByMember(Long memberId) {
        return noteFieldRepository.findAllByMember(memberId);
    }
    
}
