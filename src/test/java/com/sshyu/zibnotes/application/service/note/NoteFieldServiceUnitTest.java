package com.sshyu.zibnotes.application.service.note;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnotes.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnotes.domain.note.exception.NoteFieldNotFoundException;
import com.sshyu.zibnotes.domain.note.model.NoteField;
import com.sshyu.zibnotes.domain.note.port.out.NoteFieldRepository;
import com.sshyu.zibnotes.fixture.MemberFixture;
import com.sshyu.zibnotes.fixture.NoteFieldFixture;
import com.sshyu.zibnotes.application.service.note.NoteFieldService;

@ExtendWith(MockitoExtension.class)
public class NoteFieldServiceUnitTest {
    
    @InjectMocks
    NoteFieldService sut;
    @Mock
    NoteFieldRepository noteFieldRepository;

    final static UUID MEMBER_ID_A = MemberFixture.MEMBER_A_ID;
    final static UUID MEMBER_ID_B = MemberFixture.MEMBER_B_ID;
    final static Long NOTE_FIELD_1_ID = NoteFieldFixture.NOTE_FIELD_1_ID;

    NoteField noteFieldBeforeSaving = NoteField.ofBasic(null, MemberFixture.validMemberA(), NoteFieldFixture.NOTE_FIELD_1_NAME, null);
    NoteField noteField1WithMemberA = NoteFieldFixture.validNoteField1OwnedByA();
    NoteField noteField2WithMemberA = NoteFieldFixture.validNoteField2OwnedByA();


    @Test
    void registerNoteField_정상_등록() {
        //given
        Long newNoteFieldId = 2222L;
        given(noteFieldRepository.save(noteFieldBeforeSaving))
            .willReturn(newNoteFieldId);

        //when/then
        assertThat(sut.registerNoteField(noteFieldBeforeSaving))
            .isEqualTo(newNoteFieldId);
    }

    @Test
    void listNoteFieldsByMember_리스트_반환() {

        given(noteFieldRepository.findAllByMemberId(MEMBER_ID_A))
            .willReturn(List.of(noteField1WithMemberA, noteField2WithMemberA));

        assertThat(sut.listNoteFieldsByMember(MEMBER_ID_A))
            .hasSize(2)
            .contains(noteField1WithMemberA, noteField2WithMemberA);
    }

    @Test
    void listNoteFieldsByMember_빈_리스트_반환() {

        given(noteFieldRepository.findAllByMemberId(MEMBER_ID_A))
            .willReturn(List.of());

        assertThat(sut.listNoteFieldsByMember(MEMBER_ID_A))
            .hasSize(0);
    }

    @Test
    void softDeleteNoteField_정상적인_삭제() {
        //given
        given(noteFieldRepository.findByNoteFieldId(NOTE_FIELD_1_ID))
            .willReturn(noteField1WithMemberA);

        // when
        sut.softDeleteNoteField(NOTE_FIELD_1_ID, MEMBER_ID_A);

        // then
        then(noteFieldRepository)
            .should()
            .softDeleteByNoteFieldId(eq(NOTE_FIELD_1_ID));
    }

    @Test
    void softDeleteNoteField_권한없는_NoteField_삭제() {

        given(noteFieldRepository.findByNoteFieldId(NOTE_FIELD_1_ID))
            .willReturn(noteField1WithMemberA);

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.softDeleteNoteField(NOTE_FIELD_1_ID, MEMBER_ID_B));
    }

    @Test
    void softDeleteNoteField_존재하지_않는_ID로_시도시_예외_발생() {

        given(noteFieldRepository.findByNoteFieldId(NOTE_FIELD_1_ID))
            .willThrow(NoteFieldNotFoundException.class);

        assertThrows(NoteFieldNotFoundException.class, () ->
            sut.softDeleteNoteField(NOTE_FIELD_1_ID, MEMBER_ID_A));
    }

}
