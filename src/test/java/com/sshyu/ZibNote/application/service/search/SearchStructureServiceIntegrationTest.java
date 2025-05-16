package com.sshyu.zibnote.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.search.SearchPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.search.SearchStructurePersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNotFoundException;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.structure.exception.StructureNotFoundException;
import com.sshyu.zibnote.fixture.MemberFixture;
import com.sshyu.zibnote.fixture.SearchFixture;
import com.sshyu.zibnote.fixture.SearchStructureFixture;
import com.sshyu.zibnote.fixture.StructureFixture;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class SearchStructureServiceIntegrationTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    SearchStructureService sut;
    @Autowired
    SearchStructurePersistenceAdapter searchStructurePersistenceAdapter;
    @Autowired
    SearchPersistenceAdapter searchPersistenceAdapter;
    @Autowired
    StructurePersistenceAdapter structurePersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    Long memberAId;
    Long memberBId;
    Long searchId;
    Long structureId1;
    Long structureId2;
    SearchStructure validSearchStructure1;
    SearchStructure validSearchStructure2;
    SearchStructure invalidSearchStructure;


    @BeforeEach
    void setUp() {
        memberAId = memberPersistenceAdapter.save(MemberFixture.of(null, MemberFixture.NAME_A));
        memberBId = memberPersistenceAdapter.save(MemberFixture.of(null, MemberFixture.NAME_B));

        searchId = searchPersistenceAdapter.save(SearchFixture.of(null, memberAId, SearchFixture.TITLE_1, SearchFixture.REGION_1));

        structureId1 = structurePersistenceAdapter.save(StructureFixture.ofStructureAptSollWithoutId());
        structureId2 = structurePersistenceAdapter.save(StructureFixture.ofStructureBaekduAptWithoutId());

        validSearchStructure1 = SearchStructureFixture.of(null, searchId, structureId1);
        validSearchStructure2 = SearchStructureFixture.of(null, searchId, structureId1);
        invalidSearchStructure = SearchStructureFixture.of(null, searchId, structureId1+ 200);
    }

    @Test
    void registerSearchStructure_정상_등록() {
        //when
        Long searchStructureId = sut.registerSearchStructure(validSearchStructure1, memberAId);

        em.flush();
        em.clear();;

        //then
        SearchStructure selectedSearchStructure = searchStructurePersistenceAdapter.findBySearchStructureId(searchStructureId);
        assertThat(selectedSearchStructure.getSearch().getSearchId()).isEqualTo(searchId);
        assertThat(selectedSearchStructure.getStructure().getStructureId()).isEqualTo(structureId1);
    }

    @Test
    void registerSearchStructure_비인가_사용자_Search_접근시_예외_발생() {
        
        assertThrows(UnauthorizedAccessException.class, () ->
            sut.registerSearchStructure(validSearchStructure1, memberBId));
    }

    @Test
    void registerSearchStructure_Structure_존재하지_않을시_예외_발생() {

        assertThrows(StructureNotFoundException.class, () ->
            sut.registerSearchStructure(invalidSearchStructure, memberAId));
    }

    @Test
    void listSearchStructuresBySearch_리스트_반환() {

        searchStructurePersistenceAdapter.save(validSearchStructure1);
        searchStructurePersistenceAdapter.save(validSearchStructure2);

        assertThat(sut.listSearchStructuresBySearch(searchId, memberAId))
            .hasSize(2);
    }

    @Test
    void listSearchStructuresBySearch_비어있는_리스트_반환() {

        assertThat(sut.listSearchStructuresBySearch(searchId, memberAId))
            .hasSize(0);
    }

    @Test
    void softDeleteSearchStructure_정상_삭제() {
        //given
        Long searchStructureId = searchStructurePersistenceAdapter.save(validSearchStructure1);

        //when
        sut.softDeleteSearchStructure(searchStructureId, memberAId);

        em.flush();
        em.clear();

        //then
        assertThrows(SearchStructureNotFoundException.class, () ->
            searchStructurePersistenceAdapter.findBySearchStructureId(searchStructureId));
    }

    @Test
    void softDeleteSearchStructure_존재하지_않는_SearchStructure_삭제_시도시_예외_발생() {

        Long notExistSearchStructureId = 555L;

        assertThrows(SearchStructureNotFoundException.class, () ->
            sut.softDeleteSearchStructure(notExistSearchStructureId, memberAId));
    }

    @Test
    void assertSearchStructureOwner_정상_사용자_접근() {

        Long searchStructureId = searchStructurePersistenceAdapter.save(validSearchStructure1);

        sut.assertSearchStructureOwner(searchStructureId, memberAId);
    }

    @Test
    void assertSearchStructureOwner_비인가_사용자_접근시_예외_발생() {

        Long searchStructureId = searchStructurePersistenceAdapter.save(validSearchStructure1);

        assertThrows(UnauthorizedAccessException.class, () ->
            sut.assertSearchStructureOwner(searchStructureId, memberBId));
    }

}
