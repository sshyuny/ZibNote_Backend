package com.sshyu.zibnote.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.search.SearchPersistenceAdapter;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.fixture.MemberFixture;
import com.sshyu.zibnote.fixture.SearchFixture;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class SearchServiceIntegrationTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    SearchService sut;
    @Autowired
    SearchPersistenceAdapter searchPersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    Long memberAId;
    Long memberBId;
    UUID search1Id;
    UUID search2Id;

    
    @BeforeEach
    void setUp() {
        memberAId = memberPersistenceAdapter.save(Member.ofBasic(null, MemberFixture.MEMBER_A_NAME));
        memberBId = memberPersistenceAdapter.save(Member.ofBasic(null, MemberFixture.MEMBER_B_NAME));
        
        Search search1 = Search.ofBasic(null, Member.onlyId(memberAId), SearchFixture.SEARCH_1_TITLE, SearchFixture.SEARCH_1_REGION, null);
        Search search2 = Search.ofBasic(null, Member.onlyId(memberAId), SearchFixture.SEARCH_2_TITLE, SearchFixture.SEARCH_2_REGION, null);
        search1Id = searchPersistenceAdapter.save(search1);
        search2Id = searchPersistenceAdapter.save(search2);
    }

    @Test
    void registerSearch_정상_등록() {
        //when
        Search search = Search.ofBasic(null, Member.onlyId(memberAId), SearchFixture.SEARCH_1_TITLE, SearchFixture.SEARCH_1_REGION, null);
        UUID searchId = sut.registerSearch(search);

        em.flush();
        em.clear();

        //then
        Search selectedSearch = searchPersistenceAdapter.findBySearchId(searchId);
        assertThat(selectedSearch.getMember().getMemberId()).isEqualTo(memberAId);
        assertThat(selectedSearch.getTitle()).isEqualTo(SearchFixture.SEARCH_1_TITLE);
        assertThat(selectedSearch.getRegion()).isEqualTo(SearchFixture.SEARCH_1_REGION);
    }

    @Test
    void getSearch_정상_조회() {
        em.flush();
        em.clear();

        //when
        Search selectedSearch1 = sut.getSearch(search1Id);

        //then
        assertThat(selectedSearch1.getMember().getMemberId()).isEqualTo(memberAId);
        assertThat(selectedSearch1.getTitle()).isEqualTo(SearchFixture.SEARCH_1_TITLE);
        assertThat(selectedSearch1.getRegion()).isEqualTo(SearchFixture.SEARCH_1_REGION);
    }

    @DisplayName("ID와 일치하는 Search 없는 경우 예외 발생")
    @Test
    void getSearch_Search_없는_경우_예외_발생() {
        em.flush();
        em.clear();

        assertThrows(SearchNotFoundException.class, () ->
            sut.getSearch(UUID.randomUUID()));
    }

    @Test
    void listSearchesByMember_리스트_조회() {
        em.flush();
        em.clear();

        List<Search> list = sut.listSearchesByMember(memberAId);

        assertThat(list).hasSize(2);
    }

    @Test
    void listSearchesByMember_빈_리스트_조회() {
        em.flush();
        em.clear();

        List<Search> list = sut.listSearchesByMember(memberBId);

        assertThat(list).hasSize(0);
    }

    @Test
    void softDeleteSearch_정상_삭제() {
        //when
        sut.softDeleteSearch(search1Id, memberAId);

        em.flush();
        em.clear();

        //then
        assertThrows(SearchNotFoundException.class, () -> 
            searchPersistenceAdapter.findBySearchId(search1Id));
    }

    @Test
    void softDeleteSearch_비인가_사용자_삭제_시도시_예외_발생() {
        em.flush();
        em.clear();

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.softDeleteSearch(search1Id, memberBId));
    }

    @Test
    void assertSearchOwner_Search_정상_사용자_접근() {
        em.flush();
        em.clear();

        //when
        Search search = sut.assertSearchOwner(search1Id, memberAId);

        //then
        assertThat(search.getMember().getMemberId())
            .isEqualTo(memberAId);
    }

    @Test
    void assertSearchOwner_Search_비인가_사용자_접근시_예외_발생() {
        em.flush();
        em.clear();

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.assertSearchOwner(search1Id, memberBId));
    }

}
