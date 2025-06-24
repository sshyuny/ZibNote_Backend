package com.sshyu.zibnotes.application.service.search;

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

import com.sshyu.zibnotes.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnotes.domain.member.model.Member;
import com.sshyu.zibnotes.domain.search.exception.InvalidSearchException;
import com.sshyu.zibnotes.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnotes.domain.search.model.Search;
import com.sshyu.zibnotes.domain.search.port.out.SearchRepository;
import com.sshyu.zibnotes.fixture.MemberFixture;
import com.sshyu.zibnotes.fixture.SearchFixture;
import com.sshyu.zibnotes.application.service.search.SearchService;

@ExtendWith(MockitoExtension.class)
public class SearchServiceUnitTest {
    
    @InjectMocks
    SearchService sut;
    @Mock
    SearchRepository searchRepository;

    final static UUID MEMBER_A_ID = MemberFixture.MEMBER_A_ID;
    final static UUID MEMBER_B_ID = MemberFixture.MEMBER_B_ID;
    final static UUID SEARCH_ID_OF_MEMBER_A = SearchFixture.SEARCH_1_ID;
    final static UUID INVALID_SEARCH_ID = UUID.randomUUID();

    Search search1WithMemberA = SearchFixture.validSearch1OwnedByA();
    Search search2WithMemberA = SearchFixture.validSearch2OwnedByA();
    Search searchWithoutId = Search.ofBasic(null, Member.onlyId(MEMBER_A_ID), SearchFixture.SEARCH_1_TITLE, SearchFixture.SEARCH_1_REGION, null);


    @Test
    void registerSearch_정상_등록() {

        given(searchRepository.save(searchWithoutId))
            .willReturn(SEARCH_ID_OF_MEMBER_A);

        assertThat(sut.registerSearch(searchWithoutId))
            .isEqualTo(SEARCH_ID_OF_MEMBER_A);
    }

    @DisplayName("ID와 일치하는 Search 없는 경우 예외 발생")
    @Test
    void getSearch_Search_없는_경우_예외_발생() {

        given(searchRepository.findBySearchId(INVALID_SEARCH_ID))
            .willThrow(SearchNotFoundException.class);

        assertThrows(SearchNotFoundException.class, () ->
            sut.getSearch(INVALID_SEARCH_ID));
    }

    @Test
    void getSearch_정상_조회() {

        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        assertThat(sut.getSearch(SEARCH_ID_OF_MEMBER_A).getSearchId())
            .isEqualTo(SEARCH_ID_OF_MEMBER_A);
    }

    @Test
    void listSearchesByMember_빈리스트_조회() {

        given(searchRepository.findAllByMemberId(MEMBER_A_ID))
            .willReturn(List.of());

        assertThat(sut.listSearchesByMember(MEMBER_A_ID))
            .hasSize(0);
    }

    @Test
    void listSearchesByMember_여러데이터_조회() {
        //given
        List<Search> list = List.of(search1WithMemberA, search2WithMemberA);
        given(searchRepository.findAllByMemberId(MEMBER_A_ID))
            .willReturn(list);

        //when/then
        assertThat(sut.listSearchesByMember(MEMBER_A_ID))
            .hasSize(2)
            .contains(search1WithMemberA, search2WithMemberA);
    }

    @Test
    void update_정상_요청() {
        //given
        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        Search newSearch = Search.ofBasic(SEARCH_ID_OF_MEMBER_A, null, "new title", "new region", "new description");

        //when/then
        assertDoesNotThrow(() -> sut.updateSearch(newSearch, MEMBER_A_ID));
    }

    @Test
    void update_비인가_사용자_시도시_예외_발생() {
        //given
        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        Search newSearch = Search.ofBasic(SEARCH_ID_OF_MEMBER_A, null, "new title", "new region", "new description");

        //when/then
        assertThrows(UnauthorizedAccessException.class, () ->
            sut.updateSearch(newSearch, MEMBER_B_ID));
    }

    @Test
    void update_유효성_검사_실패시_예외_발생() {
        //given
        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        Search newSearch = Search.ofBasic(SEARCH_ID_OF_MEMBER_A, null, null, "new region", "new description");

        //when/then
        assertThrows(InvalidSearchException.class, () -> 
            sut.updateSearch(newSearch, MEMBER_A_ID));
    }

    @Test
    void softDeleteSearch_정상_삭제() {

        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        sut.softDeleteSearch(SEARCH_ID_OF_MEMBER_A, MEMBER_A_ID);
    }

    @Test
    void softDeleteSearch_비인가_사용자_삭제_시도시_예외_발생() {

        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        assertThrows(UnauthorizedAccessException.class, () ->
            sut.softDeleteSearch(SEARCH_ID_OF_MEMBER_A, MEMBER_B_ID));
    }

    @Test
    void assertSearchOwner_Search_정상_사용자_접근() {

        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        sut.assertSearchOwner(SEARCH_ID_OF_MEMBER_A, MEMBER_A_ID);
    }

    @Test
    void assertSearchOwner_Search_비인가_사용자_접근시_예외_발생() {

        given(searchRepository.findBySearchId(SEARCH_ID_OF_MEMBER_A))
            .willReturn(search1WithMemberA);

        assertThrows(UnauthorizedAccessException.class, () ->
            sut.assertSearchOwner(SEARCH_ID_OF_MEMBER_A, MEMBER_B_ID));
    }

}
