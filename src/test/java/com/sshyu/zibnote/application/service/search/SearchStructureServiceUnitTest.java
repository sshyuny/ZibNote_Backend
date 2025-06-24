package com.sshyu.zibnote.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnote.application.service.search.SearchStructureService;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.exception.InvalidSearchStructureException;
import com.sshyu.zibnote.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.port.in.SearchUseCase;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureRepository;
import com.sshyu.zibnote.domain.structure.exception.StructureNotFoundException;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.in.StructureUseCase;
import com.sshyu.zibnote.fixture.MemberFixture;
import com.sshyu.zibnote.fixture.SearchFixture;
import com.sshyu.zibnote.fixture.SearchStructureFixture;
import com.sshyu.zibnote.fixture.StructureFixture;

@ExtendWith(MockitoExtension.class)
public class SearchStructureServiceUnitTest {
    
    @InjectMocks
    SearchStructureService sut;
    @Mock
    SearchStructureRepository searchStructureRepository;
    @Mock
    SearchUseCase searchUseCase;
    @Mock
    StructureUseCase structureUseCase;

    final UUID MEMBER_A_ID = MemberFixture.MEMBER_A_ID;
    final UUID MEMBER_B_ID = MemberFixture.MEMBER_B_ID;
    final UUID SEARCH_1_ID_OF_A = SearchFixture.SEARCH_1_ID;
    final UUID SEARCH_2_ID_OF_A = SearchFixture.SEARCH_2_ID;
    final Long STRUCTURE_1_ID = StructureFixture.STRUCTURE_1_ID;
    final Long STRUCTURE_2_ID = StructureFixture.STRUCTURE_2_ID;
    final UUID SEARCH_STRUCTURE_ID_1_OF_A = SearchStructureFixture.SEARCH_STRUCTURE_1;
    final UUID SEARCH_STRUCTURE_ID_2_OF_A = SearchStructureFixture.SEARCH_STRUCTURE_2_ID;

    Structure structure = StructureFixture.validStructure1();
    Search searchOfMemberA = SearchFixture.validSearch1OwnedByA();
    SearchStructure searchStructure1WithMemberA = SearchStructureFixture.validSearchStructure1OwnedByA();
    SearchStructure searchStructure2WithMemberA = SearchStructureFixture.validSearchStructure1OwnedByA();
    SearchStructure invalidSearchStructure = SearchStructure.ofBasic(null, Search.onlyId(null), Structure.onlyId(null), null);


    @Test
    void registerSearchStructure_정상_등록() {
        //given
        final UUID searchStructureId = UUID.randomUUID();
        given(searchUseCase.getSearch(SEARCH_1_ID_OF_A))
            .willReturn(searchOfMemberA);
        given(searchStructureRepository.save(searchStructure1WithMemberA))
            .willReturn(searchStructureId);

        //when/then
        assertThat(sut.registerSearchStructure(searchStructure1WithMemberA, MEMBER_A_ID))
            .isEqualTo(searchStructureId);
    }

    @Test
    void registerSearchStructure_비인가_사용자_Search_접근시_예외_발생() {

        given(searchUseCase.getSearch(SEARCH_1_ID_OF_A))
            .willReturn(searchOfMemberA);

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.registerSearchStructure(searchStructure1WithMemberA, MEMBER_B_ID)
        );
    }

    @DisplayName("Structure과 Search 필드 null로 요청시 예외 발생")
    @Test
    void registerSearchStructure_유효성_검사_실패시_예외_발생() {

        assertThrows(InvalidSearchStructureException.class, () -> 
            sut.registerSearchStructure(invalidSearchStructure, MEMBER_A_ID)
        );
    }

    @Test
    void registerSearchStructure_Search_존재하지_않을시_예외_발생() {

        given(searchUseCase.getSearch(SEARCH_1_ID_OF_A))
            .willThrow(SearchNotFoundException.class);

        assertThrows(SearchNotFoundException.class, () -> 
            sut.registerSearchStructure(searchStructure1WithMemberA, MEMBER_A_ID));
    }

    @Test
    void registerSearchStructure_Structure_존재하지_않을시_예외_발생() {
        //given
        given(searchUseCase.getSearch(SEARCH_1_ID_OF_A))
            .willReturn(searchOfMemberA);
        given(structureUseCase.getStructure(STRUCTURE_1_ID))
            .willThrow(StructureNotFoundException.class);

        //when/then
        assertThrows(StructureNotFoundException.class, () ->
            sut.registerSearchStructure(searchStructure1WithMemberA, MEMBER_A_ID));
    }

    @Test
    void listSearchStructuresBySearch_리스트_반환() {
        //given
        given(searchUseCase.getSearch(SEARCH_1_ID_OF_A))
            .willReturn(searchOfMemberA);
        given(searchStructureRepository.findAllBySearchId(SEARCH_1_ID_OF_A))
            .willReturn(List.of(searchStructure1WithMemberA, searchStructure2WithMemberA));

        //when/then
        assertThat(sut.listSearchStructuresBySearch(SEARCH_1_ID_OF_A, MEMBER_A_ID))
            .hasSize(2)
            .contains(searchStructure1WithMemberA, searchStructure2WithMemberA);
    }

    @Test
    void listSearchStructuresBySearch_비어있는_리스트_반환() {
        //given
        given(searchUseCase.getSearch(SEARCH_1_ID_OF_A))
            .willReturn(searchOfMemberA);
        given(searchStructureRepository.findAllBySearchId(SEARCH_1_ID_OF_A))
            .willReturn(List.of());

        //when/then
        assertThat(sut.listSearchStructuresBySearch(SEARCH_1_ID_OF_A, MEMBER_A_ID))
            .hasSize(0);
    }

    @Test
    void listSearchStructuresBySearch_비인가_사용자_Search_접근시_예외_발생() {

        given(searchUseCase.getSearch(SEARCH_1_ID_OF_A))
            .willReturn(searchOfMemberA);

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.listSearchStructuresBySearch(SEARCH_1_ID_OF_A, MEMBER_B_ID)
        );
    }

    @Test
    void softDeleteSearchStructure_정상_삭제() {

        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);

        sut.softDeleteSearchStructure(SEARCH_STRUCTURE_ID_1_OF_A, MEMBER_A_ID);
    }

    @Test
    void softDeleteSearchStructure_비인가_사용자_Search_접근시_예외_발생() {
        //given
        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);
        given(searchUseCase.assertSearchOwner(SEARCH_1_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);

        //when/then
        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.softDeleteSearchStructure(SEARCH_STRUCTURE_ID_1_OF_A, MEMBER_B_ID)
        );
    }

    @Test
    void assertSearchStructureOwner_정상_사용자_접근() {
        //given
        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);
        
        //when
        SearchStructure searchStructure = sut.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_1_OF_A, MEMBER_A_ID);

        //then
        assertThat(searchStructure.getSearchStructureId()).isEqualTo(searchStructure1WithMemberA.getSearchStructureId());
    }

    @Test
    void assertSearchStructureOwner_비인가_사용자_접근시_예외_발생() {
        //given
        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);
        given(searchUseCase.assertSearchOwner(SEARCH_1_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);

        //when/then
        assertThrows(UnauthorizedAccessException.class, () ->
            sut.assertSearchStructureOwner(SEARCH_STRUCTURE_ID_1_OF_A, MEMBER_B_ID));
    }

    @Test
    void updateSearchStructure_정상_요청() {
        //given
        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);
        SearchStructure searchStructureForUpdate = SearchStructure.ofBasic(
            SEARCH_STRUCTURE_ID_1_OF_A, null, Structure.onlyId(STRUCTURE_2_ID), "test"
        );

        //when/then
        assertDoesNotThrow(() -> 
            sut.updateSearchStructure(searchStructureForUpdate, MEMBER_A_ID));
    }

    @Test
    void updateSearchStructure_비인가_사용자_요청시_예외_발생() {
        //given
        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);
        given(searchUseCase.assertSearchOwner(SEARCH_1_ID_OF_A, MEMBER_B_ID))
            .willThrow(UnauthorizedAccessException.class);

        //when/then
        assertThrows(UnauthorizedAccessException.class, () ->
            sut.updateSearchStructure(searchStructure1WithMemberA, MEMBER_B_ID));
    }

    @Test
    void updateSearchStructure_유효하지_않은_도메인_요청시_예외_발생() {
        //given
        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);
        SearchStructure searchStructureForUpdate = SearchStructure.ofBasic(
            SEARCH_STRUCTURE_ID_1_OF_A, Search.onlyId(SEARCH_1_ID_OF_A), Structure.onlyId(STRUCTURE_2_ID), "test"
        );

        //when/then
        assertThrows(InvalidSearchStructureException.class, () -> 
            sut.updateSearchStructure(searchStructureForUpdate, MEMBER_A_ID));
    }

    @Test
    void updateSearchStructure_존재하지_않는_Structure_요청시_예외_발생() {
        //given
        given(searchStructureRepository.findBySearchStructureId(SEARCH_STRUCTURE_ID_1_OF_A))
            .willReturn(searchStructure1WithMemberA);
        given(structureUseCase.getStructure(STRUCTURE_1_ID))
            .willThrow(StructureNotFoundException.class);
        SearchStructure searchStructureForUpdate = SearchStructure.ofBasic(
            SEARCH_STRUCTURE_ID_1_OF_A, null, Structure.onlyId(STRUCTURE_1_ID), "test"
        );

        //when/then
        assertThrows(StructureNotFoundException.class, () -> 
            sut.updateSearchStructure(searchStructureForUpdate, MEMBER_A_ID));
    }

}
