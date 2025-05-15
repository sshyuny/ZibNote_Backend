package com.sshyu.zibnote.application.service.note;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.note.port.out.NoteFieldRepository;

@ExtendWith(MockitoExtension.class)
public class NoteFieldServiceUnitTest {
    
    @Mock
    NoteFieldRepository noteFieldRepository;
    @InjectMocks
    NoteFieldService noteFieldService;

    final static Long MEMBER_ID_1 = 1L;
    final static Long MEMBER_ID_2 = 22L;
    final static Long NOTE_FIELD_ID = 237L;
    final static String NOTE_FIELD_NAME = "놀이터";
    final static String NOTE_FIELD_DESCRIPTION = "놀이터 상태";

    @BeforeEach
    void beforeEach() {
        NoteField savedNoteField = NoteField.builder()
            .noteFieldId(NOTE_FIELD_ID)
            .member(Member.onlyId(MEMBER_ID_1))
            .name(NOTE_FIELD_NAME)
            .description(NOTE_FIELD_DESCRIPTION)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .isDeleted(0)
            .build();

        given(noteFieldRepository.findByNoteFieldId(NOTE_FIELD_ID))
            .willReturn(savedNoteField); 
    }


    @Test
    void softDeleteNoteField_권한없는_NoteField_삭제() {
        // when / then
        assertThrows(UnauthorizedAccessException.class, () -> 
            noteFieldService.softDeleteNoteField(NOTE_FIELD_ID, MEMBER_ID_2));
    }

    @Test
    void softDeleteNoteField_정상적인_삭제() {
        // when
        noteFieldService.softDeleteNoteField(NOTE_FIELD_ID, MEMBER_ID_1);

        // then
        then(noteFieldRepository)
            .should()
            .softDeleteByNoteFieldId(eq(NOTE_FIELD_ID));
    }
}
