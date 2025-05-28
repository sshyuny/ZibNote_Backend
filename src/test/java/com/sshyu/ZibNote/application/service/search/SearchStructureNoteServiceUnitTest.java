package com.sshyu.zibnote.application.service.search;

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

import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.exception.InvalidSearchStructureNoteException;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureNoteRepository;
import com.sshyu.zibnote.fixture.MemberFixture;
import com.sshyu.zibnote.fixture.SearchStructureFixture;
import com.sshyu.zibnote.fixture.SearchStructureNoteFixture;

@ExtendWith(MockitoExtension.class)
public class SearchStructureNoteServiceUnitTest {

    @InjectMocks
    SearchStructureNoteService sut;
    @Mock
    SearchStructureUseCase searchStructureUseCase;
    @Mock
    SearchStructureNoteRepository searchStructureNoteRepository;

    final UUID MEMBER_A_ID = MemberFixture.MEMBER_A_ID;
    final UUID MEMBER_B_ID = MemberFixture.MEMBER_B_ID;
    final UUID SEARCH_STRUCTURE_ID_OF_A = SearchStructureFixture.SEARCH_STRUCTURE_1;

    final UUID SAVED_NOTE_ID_OF_A_1 = SearchStructureNoteFixture.NOTE_ID_OF_MEMBER_A_1;
    final UUID SAVED_NOTE_ID_OF_A_2 = SearchStructureNoteFixture.NOTE_ID_OF_MEMBER_A_2;
    final UUID SAMPLE_NOTE_ID = UUID.randomUUID();

    SearchStructureNote validNoteOfMemberA = SearchStructureNoteFixture.validNoteWithoutId();
    SearchStructureNote invalidNote = SearchStructureNoteFixture.invalidNote();
    SearchStructureNote savedNote1 = SearchStructureNoteFixture.validNote1OwnedByA();
    SearchStructureNote savedNote2 = SearchStructureNoteFixture.validNote2OwnedByA();


    @Test
    void registerSearchStructureNote_도메인_검증_실패시_예외_발생() {

        assertThrows(InvalidSearchStructureNoteException.class, () -> 
            sut.registerSearchStructureNote(invalidNote, MEMBER_A_ID));
    }

    @Test
    void registerSearchStructureNote_비인가_사용자_Search_접근시_예외_발생() {

        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);
        
        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.registerSearchStructureNote(validNoteOfMemberA, MEMBER_B_ID));
    }

    @Test
    void registerSearchStructureNote_정상_등록시_ID반환() {

        given(searchStructureNoteRepository.save(validNoteOfMemberA))
            .willReturn(SAMPLE_NOTE_ID);

        assertThat(sut.registerSearchStructureNote(validNoteOfMemberA, MEMBER_A_ID))
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

}
