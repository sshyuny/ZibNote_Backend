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
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.port.out.SearchRepository;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    
    @InjectMocks
    SearchService searchService;
    @Mock
    SearchRepository searchRepository;

    final static Long MEMBER_A = 555L;
    final static Long MEMBER_B = 777L;
    final static Long SEARCH_ID_OF_A = 22L;
    Search searchOfMemberA = Search.builder()
        .searchId(SEARCH_ID_OF_A)
        .member(Member.builder().memberId(MEMBER_A).build())
        .build();

    @Test
    void assertSearchOwner_Search_주인과_로그인계정_일치() {

        given(searchRepository.findBySearchId(SEARCH_ID_OF_A))
            .willReturn(searchOfMemberA);

        searchService.assertSearchOwner(SEARCH_ID_OF_A, MEMBER_A);
    }

    @Test
    void assertSearchOwner_Search_주인과_로그인계정_불일치() {

        given(searchRepository.findBySearchId(SEARCH_ID_OF_A))
            .willReturn(searchOfMemberA);

        assertThrows(UnauthorizedAccessException.class, () ->
            searchService.assertSearchOwner(SEARCH_ID_OF_A, MEMBER_B));
    }

}
