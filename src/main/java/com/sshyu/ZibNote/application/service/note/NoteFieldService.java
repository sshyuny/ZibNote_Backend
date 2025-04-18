package com.sshyu.zibnote.application.service.note;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.note.port.in.NoteFieldUseCase;
import com.sshyu.zibnote.domain.note.port.out.NoteFieldRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoteFieldService implements NoteFieldUseCase {

    private final NoteFieldRepository noteFieldRepository;
    private final AuthUseCase authUseCase;

    @Override
    public void registerNoteField(NoteField noteField) {
        noteField.prepareForCreation();
        noteFieldRepository.save(noteField);
    }

    @Override
    public List<NoteField> listNoteFieldsByMember(Long memberId) {
        return noteFieldRepository.findAllByMember(memberId);
    }

    @Override
    public void softDeleteNoteField(NoteField requestedNoteField) {

        NoteField selectedNoteField = noteFieldRepository.findByNoteFieldId(requestedNoteField.getNoteFieldId());
        
        selectedNoteField.assureOwner(authUseCase.getMemberId());

        noteFieldRepository.softDeleteByNoteFieldId(selectedNoteField.getNoteFieldId(), LocalDateTime.now());
    }
    
}
