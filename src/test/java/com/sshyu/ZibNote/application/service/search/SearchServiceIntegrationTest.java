package com.sshyu.zibnote.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.search.SearchPersistenceAdapter;
import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
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
    Long search1Id;
    Long search2Id;

    
    @BeforeEach
    void setUp() {
        memberAId = memberPersistenceAdapter.save(MemberFixture.of(null, MemberFixture.NAME_A));
        memberBId = memberPersistenceAdapter.save(MemberFixture.of(null, MemberFixture.NAME_B));
        
        Search search1 = SearchFixture.of(null, memberAId, SearchFixture.TITLE_1, SearchFixture.REGION_1);
        Search search2 = SearchFixture.of(null, memberAId, SearchFixture.TITLE_2, SearchFixture.REGION_2);
        search1Id = searchPersistenceAdapter.save(search1);
        search2Id = searchPersistenceAdapter.save(search2);
    }

    @Test
    void registerSearch_정상_등록() {
        //when
        Search search = SearchFixture.of(null, memberAId, SearchFixture.TITLE_1, SearchFixture.REGION_1);
        Long searchId = sut.registerSearch(search);

        em.flush();
        em.clear();

        //then
        Search selectedSearch = searchPersistenceAdapter.findBySearchId(searchId);
        assertThat(selectedSearch.getMember().getMemberId()).isEqualTo(memberAId);
        assertThat(selectedSearch.getTitle()).isEqualTo(SearchFixture.TITLE_1);
        assertThat(selectedSearch.getRegion()).isEqualTo(SearchFixture.REGION_1);
    }

    @Test
    void getSearch_정상_조회() {
        em.flush();
        em.clear();

        //when
        Search selectedSearch1 = sut.getSearch(search1Id);

        //then
        assertThat(selectedSearch1.getMember().getMemberId()).isEqualTo(memberAId);
        assertThat(selectedSearch1.getTitle()).isEqualTo(SearchFixture.TITLE_1);
        assertThat(selectedSearch1.getRegion()).isEqualTo(SearchFixture.REGION_1);
    }

    @DisplayName("ID와 일치하는 Search 없는 경우 예외 발생")
    @Test
    void getSearch_Search_없는_경우_예외_발생() {
        em.flush();
        em.clear();

        assertThrows(SearchNotFoundException.class, () ->
            sut.getSearch(888888L));
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
