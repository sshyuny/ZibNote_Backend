package com.sshyu.zibnote.application.service.note;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.note.exception.NoteFieldNotFoundException;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.note.port.in.NoteFieldUseCase;
import com.sshyu.zibnote.domain.note.port.out.NoteFieldRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoteFieldService implements NoteFieldUseCase {

    private final NoteFieldRepository noteFieldRepository;

    /**
     * NoteField를 등록한다.
     * 
     * @param noteField 등록하려는 NoteField
     */
    @Override
    public Long registerNoteField(final NoteField noteField) {

        return noteFieldRepository.save(noteField);
    }

    /**
     * 로그인한 사용자가 등록한 NoteField 목록을 조회한다.
     * 
     * @param memberId 로그인한 사용자 ID
     * @return 로그인한 사용자가 등록한 NoteField 목록
     */
    @Override
    public List<NoteField> listNoteFieldsByMember(final UUID memberId) {

        return noteFieldRepository.findAllByMemberId(memberId);
    }

    /**
     * NoteField를 삭제한다.
     * 
     * <ol>
     *   <li>로그인한 사용자가 NoteField에 접근 가능한지 확인</li>
     *   <li>NoteFiled 삭제</li>
     * </ol>
     * 
     * @param noteFieldId 삭제하려는 NoteField ID
     * @param memberId 로그인한 사용자 ID
     * @throws NoteFieldNotFoundException 일치하는 NoteField가 없을 경우
     */
    @Override
    public void softDeleteNoteField(final Long noteFieldId, final UUID memberId) {

        final NoteField selectedNoteField = noteFieldRepository.findByNoteFieldId(noteFieldId);
        
        selectedNoteField.assureOwner(memberId);

        noteFieldRepository.softDeleteByNoteFieldId(selectedNoteField.getNoteFieldId());
    }
    
}
