package com.sshyu.zibnotes.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnotes.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnotes.domain.search.exception.InvalidSearchStructureNoteException;
import com.sshyu.zibnotes.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnotes.domain.search.model.EvalType;
import com.sshyu.zibnotes.domain.search.model.SearchStructureNote;
import com.sshyu.zibnotes.domain.search.port.in.SearchStructureUseCase;
import com.sshyu.zibnotes.domain.search.port.out.SearchStructureNoteRepository;
import com.sshyu.zibnotes.fixture.MemberFixture;
import com.sshyu.zibnotes.fixture.NoteFieldFixture;
import com.sshyu.zibnotes.fixture.SearchStructureFixture;
import com.sshyu.zibnotes.fixture.SearchStructureNoteFixture;
import com.sshyu.zibnotes.application.service.search.SearchStructureNoteService;

@ExtendWith(MockitoExtension.class)
public class SearchStructureNoteServiceUnitTest {

    @InjectMocks
    SearchStructureNoteService sut;
    @Mock
    SearchStructureUseCase searchStructureUseCase;
    @Mock
    SearchStructureNoteRepository searchStructureNoteRepository;

    final static UUID MEMBER_A_ID = MemberFixture.MEMBER_A_ID;
    final static UUID MEMBER_B_ID = MemberFixture.MEMBER_B_ID;
    final static UUID SEARCH_STRUCTURE_ID_OF_A = SearchStructureFixture.SEARCH_STRUCTURE_1;
    final static Long NOTE_FIELD_ID_OF_A = NoteFieldFixture.NOTE_FIELD_1_ID;

    final static UUID SAVED_NOTE_ID_OF_A_1 = SearchStructureNoteFixture.NOTE_ID_OF_MEMBER_A_1;
    final static UUID SAVED_NOTE_ID_OF_A_2 = SearchStructureNoteFixture.NOTE_ID_OF_MEMBER_A_2;
    final static UUID SAMPLE_NOTE_ID = UUID.randomUUID();

    SearchStructureNote validNote = SearchStructureNoteFixture.createNote(
        null, SEARCH_STRUCTURE_ID_OF_A, NOTE_FIELD_ID_OF_A);
    SearchStructureNote invalidNote1 = SearchStructureNoteFixture.createNote(
        null, SEARCH_STRUCTURE_ID_OF_A, null);
    SearchStructureNote invalidNote2 = SearchStructureNoteFixture.createNote(
        SAMPLE_NOTE_ID, SEARCH_STRUCTURE_ID_OF_A, null);

    SearchStructureNote savedNote1 = SearchStructureNoteFixture.validNote1OwnedByA();
    SearchStructureNote savedNote2 = SearchStructureNoteFixture.validNote2OwnedByA();


    @Test
    void registerSearchStructureNote_도메인_검증_실패시_예외_발생() {

        assertThrows(InvalidSearchStructureNoteException.class, () -> 
            sut.registerSearchStructureNote(invalidNote1, MEMBER_A_ID));
    }

    @Test
    void registerSearchStructureNote_비인가_사용자_Search_접근시_예외_발생() {

        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);
        
        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.registerSearchStructureNote(validNote, MEMBER_B_ID));
    }

    @Test
    void registerSearchStructureNote_EvalType과_EvalValue_언매치로_예외_발생() {

        SearchStructureNote note = SearchStructureNote.ofBasic(
            SAMPLE_NOTE_ID, SearchStructureFixture.validSearchStructure1OwnedByA(), NoteFieldFixture.validNoteField1OwnedByA(), EvalType.STAR, "7", null);

        assertThrows(InvalidSearchStructureNoteException.class, () ->
            sut.registerSearchStructureNote(note, MEMBER_A_ID));
    }

    @Test
    void registerSearchStructureNote_정상_등록시_ID반환() {

        given(searchStructureNoteRepository.save(validNote))
            .willReturn(SAMPLE_NOTE_ID);

        assertThat(sut.registerSearchStructureNote(validNote, MEMBER_A_ID))
            .isEqualTo(SAMPLE_NOTE_ID);
    }

    @Test
    void listSearchStructureNotesBySearchStructure_비인가_사용자_Search_접근시_예외_발생() {

        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);

        assertThrows(UnauthorizedAccessException.class, () ->
            sut.listSearchStructureNotesBySearchStructure(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B_ID));
    }

    @Test
    void listSearchStructureNotesBySearchStructure_정상_조회() {

        final List<SearchStructureNote> notes = List.of(savedNote1, savedNote2);

        given(searchStructureNoteRepository.findAllBySearchStructureId(SEARCH_STRUCTURE_ID_OF_A))
            .willReturn(notes);

        final List<SearchStructureNote> result = sut.listSearchStructureNotesBySearchStructure(SEARCH_STRUCTURE_ID_OF_A, MEMBER_A_ID);

        assertThat(result).hasSize(2)
            .extracting("searchStructureNoteId")
            .containsExactly(SAVED_NOTE_ID_OF_A_1, SAVED_NOTE_ID_OF_A_2);
    }

    @Test
    void softDeleteSearchStructureNote_요청된_ID값의_엔티티_없을경우_예외_발생() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAMPLE_NOTE_ID))
            .willThrow(SearchStructureNoteNotFoundException.class);

        assertThrows(SearchStructureNoteNotFoundException.class, () -> 
            sut.softDeleteSearchStructureNote(SAMPLE_NOTE_ID, MEMBER_A_ID));
    }

    @Test
    void softDeleteSearchStructureNote_비인가_사용자_Search_접근시_예외_발생() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAMPLE_NOTE_ID))
            .willReturn(savedNote1);
        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.softDeleteSearchStructureNote(SAMPLE_NOTE_ID, MEMBER_B_ID));
    }

    @Test
    void softDeleteSearchStructureNote_정상_삭제() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAMPLE_NOTE_ID))
            .willReturn(savedNote1);
        
        assertDoesNotThrow(() -> sut.softDeleteSearchStructureNote(SAMPLE_NOTE_ID, MEMBER_A_ID));
    }

    @Test
    void updateSearchStructureNote_정상_수정() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAVED_NOTE_ID_OF_A_1))
            .willReturn(savedNote1);

        assertDoesNotThrow(() -> sut.updateSearchStructureNote(savedNote1, MEMBER_A_ID));
    }

    @Test
    void updateSearchStructureNote_유효성검사_실패시_예외_발생() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAMPLE_NOTE_ID))
            .willReturn(invalidNote2);

        assertThrows(InvalidSearchStructureNoteException.class, () -> 
            sut.updateSearchStructureNote(invalidNote2, MEMBER_A_ID));
    }

    @Test
    void updateSearchStructureNote_비인가_사용자_접근시_예외_발생() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAVED_NOTE_ID_OF_A_1))
            .willReturn(savedNote1);
        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);
        
        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.updateSearchStructureNote(savedNote1, MEMBER_B_ID));
    }

    @Test
    void updateSearchStructureNote_EvalType과_EvalValue_언매치로_예외_발생() {

        SearchStructureNote note = SearchStructureNote.ofBasic(
            SAVED_NOTE_ID_OF_A_1, SearchStructureFixture.validSearchStructure1OwnedByA(), NoteFieldFixture.validNoteField1OwnedByA(), EvalType.STAR, "7", null);
        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAVED_NOTE_ID_OF_A_1))
            .willReturn(savedNote1);

        assertThrows(InvalidSearchStructureNoteException.class, () -> 
            sut.updateSearchStructureNote(note, MEMBER_A_ID));
    }

}
