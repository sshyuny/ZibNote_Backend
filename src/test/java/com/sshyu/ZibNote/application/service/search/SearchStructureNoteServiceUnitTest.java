package com.sshyu.zibnote.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.exception.NotValidSearchStructureNoteException;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.search.port.in.SearchStructureUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureNoteRepository;
import com.sshyu.zibnote.domain.structure.model.Structure;

@ExtendWith(MockitoExtension.class)
public class SearchStructureNoteServiceUnitTest {

    @InjectMocks
    SearchStructureNoteService sut;
    @Mock
    SearchStructureUseCase searchStructureUseCase;
    @Mock
    SearchStructureNoteRepository searchStructureNoteRepository;

    final static Long MEMBER_A = 555L;
    final static Long MEMBER_B = 777L;
    final static Long SEARCH_ID_OF_A = 22L;
    final static Long SEARCH_STRUCTURE_ID_OF_A = 88993L;
    final static Long NEW_NOTE_ID = 9L;
    final static Long SAVED_NOTE_ID_OF_A_1 = 88L;
    final static Long SAVED_NOTE_ID_OF_A_2 = 89L;
    final static Long NOT_EXIST_NOTE_ID = 99999L;
    final static Long NOTE_FIELD_ID_OF_A = 237L;    
    final static Long STRUCTURE_ID = 33L;

    SearchStructureNote validNoteOfMemberA;
    SearchStructureNote invalidNote;
    SearchStructureNote savedNote1;
    SearchStructureNote savedNote2;
    
    @BeforeEach
    void setUp() {

        Member memberA = Member.builder().memberId(MEMBER_A).build();
        Structure structure = Structure.builder()
            .structureId(STRUCTURE_ID)
            .build();
        Search searchOfMemberA = Search.builder()
            .searchId(SEARCH_ID_OF_A)
            .member(memberA)
            .build();
        SearchStructure searchStructureOfMemberA = SearchStructure.builder()
            .searchStructureId(SEARCH_STRUCTURE_ID_OF_A)
            .search(searchOfMemberA)
            .structure(structure)
            .build();
        NoteField noteFieldOfMemberA = NoteField.builder()
            .noteFieldId(NOTE_FIELD_ID_OF_A)
            .member(memberA)
            .name("놀이터")
            .build();

        validNoteOfMemberA = SearchStructureNote.builder()
            .searchStructureNoteId(null)
            .searchStructure(searchStructureOfMemberA)
            .noteField(noteFieldOfMemberA)
            .build();
        invalidNote = SearchStructureNote.builder()
            .searchStructureNoteId(null)
            .searchStructure(searchStructureOfMemberA)
            .noteField(null)
            .build();
        savedNote1 = SearchStructureNote.builder()
            .searchStructureNoteId(SAVED_NOTE_ID_OF_A_1)
            .searchStructure(searchStructureOfMemberA)
            .noteField(noteFieldOfMemberA)
            .build();
        savedNote2 = SearchStructureNote.builder()
            .searchStructureNoteId(SAVED_NOTE_ID_OF_A_2)
            .searchStructure(searchStructureOfMemberA)
            .noteField(noteFieldOfMemberA)
            .build();
    }

    @Test
    void registerSearchStructureNote_도메인_검증_실패시_예외_발생() {

        assertThrows(NotValidSearchStructureNoteException.class, () -> 
            sut.registerSearchStructureNote(invalidNote, MEMBER_A));
    }

    @Test
    void registerSearchStructureNote_비인가_사용자_Search_접근시_예외_발생() {

        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B))
            .willThrow(UnauthorizedAccessException.class);
        
        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.registerSearchStructureNote(validNoteOfMemberA, MEMBER_B));
    }

    @Test
    void registerSearchStructureNote_정상_등록시_ID반환() {

        given(searchStructureNoteRepository.save(validNoteOfMemberA))
            .willReturn(NEW_NOTE_ID);

        assertThat(sut.registerSearchStructureNote(validNoteOfMemberA, MEMBER_A))
            .isEqualTo(NEW_NOTE_ID);
    }

    @Test
    void listSearchStructureNotesBySearchStructure_비인가_사용자_Search_접근시_예외_발생() {

        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B))
            .willThrow(UnauthorizedAccessException.class);

        assertThrows(UnauthorizedAccessException.class, () ->
            sut.listSearchStructureNotesBySearchStructure(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B));
    }

    @Test
    void listSearchStructureNotesBySearchStructure_정상_조회() {

        List<SearchStructureNote> notes = List.of(savedNote1, savedNote2);

        given(searchStructureNoteRepository.findAllBySearchStructureId(SEARCH_STRUCTURE_ID_OF_A))
            .willReturn(notes);

        List<SearchStructureNote> result = sut.listSearchStructureNotesBySearchStructure(SEARCH_STRUCTURE_ID_OF_A, MEMBER_A);

        assertThat(result).hasSize(2)
            .extracting("searchStructureNoteId")
            .containsExactly(SAVED_NOTE_ID_OF_A_1, SAVED_NOTE_ID_OF_A_2);
    }

    @Test
    void softDeleteSearchStructureNote_요청된_ID값의_엔티티_없을경우_예외_발생() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(NOT_EXIST_NOTE_ID))
            .willThrow(SearchStructureNoteNotFoundException.class);

        assertThrows(SearchStructureNoteNotFoundException.class, () -> 
            sut.softDeleteSearchStructureNote(NOT_EXIST_NOTE_ID, MEMBER_A));
    }

    @Test
    void softDeleteSearchStructureNote_비인가_사용자_Search_접근시_예외_발생() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAVED_NOTE_ID_OF_A_1))
            .willReturn(savedNote1);
        given(searchStructureUseCase.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B))
            .willThrow(UnauthorizedAccessException.class);

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.softDeleteSearchStructureNote(SAVED_NOTE_ID_OF_A_1, MEMBER_B));
    }

    @Test
    void softDeleteSearchStructureNote_정상_삭제() {

        given(searchStructureNoteRepository.findBySearchStructureNoteId(SAVED_NOTE_ID_OF_A_1))
            .willReturn(savedNote1);
        
        assertDoesNotThrow(() -> sut.softDeleteSearchStructureNote(SAVED_NOTE_ID_OF_A_1, MEMBER_A));
    }

}
