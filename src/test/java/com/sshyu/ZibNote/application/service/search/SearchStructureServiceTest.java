package com.sshyu.zibnote.application.service.search;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.exception.NotValidSearchStructureException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.port.in.SearchUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureRepository;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.in.StructureUseCase;

@ExtendWith(MockitoExtension.class)
public class SearchStructureServiceTest {
    
    @InjectMocks
    SearchStructureService searchStructureService;
    @Mock
    SearchStructureRepository searchStructureRepository;
    @Mock
    SearchUseCase searchUseCase;
    @Mock
    StructureUseCase structureUseCase;

    // data value
    final static Long MEMBER_A = 555L;
    final static Long SEARCH_ID_OF_A = 22L;
    final static Long SEARCH_STRUCTURE_ID_OF_A = 88993L;
    final static Long STRUCTURE_ID = 33L;
    // test value
    final static Long MEMBER_B = 789L;

    Structure structure = Structure.builder()
        .structureId(STRUCTURE_ID)
        .build();
    Search searchOfMemberA = Search.builder()
        .searchId(SEARCH_ID_OF_A)
        .member(Member.builder().memberId(MEMBER_A).build())
        .build();
    SearchStructure searchStructureOfMemberA = SearchStructure.builder()
        .searchStructureId(SEARCH_STRUCTURE_ID_OF_A)
        .search(searchOfMemberA)
        .structure(structure)
        .description(null)
        .build();
    SearchStructure emptySearchStructure = SearchStructure.builder()
        .search(Search.builder().build())
        .structure(Structure.builder().build())
        .description(null)
        .build();

        
    @Test
    void registerSearchStructure_권한없는_SEARCH_요청() {

        given(searchUseCase.getSearch(SEARCH_ID_OF_A))
            .willReturn(searchOfMemberA);

        assertThrows(UnauthorizedAccessException.class, () -> 
            searchStructureService.registerSearchStructure(searchStructureOfMemberA, MEMBER_B)
        );
    }

    @Test
    void registerSearchStructure_STRUCTURE과_SEARCH_비어있음() {

        assertThrows(NotValidSearchStructureException.class, () -> 
            searchStructureService.registerSearchStructure(emptySearchStructure, MEMBER_A)
        );
    }

    @Test
    void listSearchStructuresBySearch_권한없는_SEARCH_요청() {

        given(searchUseCase.getSearch(SEARCH_ID_OF_A))
            .willReturn(searchOfMemberA);

        assertThrows(UnauthorizedAccessException.class, () -> 
            searchStructureService.listSearchStructuresBySearch(SEARCH_ID_OF_A, MEMBER_B)
        );
    }

    @Test
    void softDeleteSearchStructure_권한없는_SEARCH_요청() {

        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_OF_A))
            .willReturn(searchStructureOfMemberA);
        given(searchUseCase.assertSearchOwner(SEARCH_ID_OF_A, MEMBER_B))
            .willThrow(UnauthorizedAccessException.class);

        assertThrows(UnauthorizedAccessException.class, () -> 
            searchStructureService.softDeleteSearchStructure(SEARCH_STRUCTURE_ID_OF_A, MEMBER_B)
        );
    }

}
