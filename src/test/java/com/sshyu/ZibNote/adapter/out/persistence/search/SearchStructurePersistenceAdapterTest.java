package com.sshyu.zibnote.adapter.out.persistence.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.fixture.StructureFixture;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({SearchStructurePersistenceAdapter.class, SearchPersistenceAdapter.class, MemberPersistenceAdapter.class, StructurePersistenceAdapter.class})
@ActiveProfiles("test")
public class SearchStructurePersistenceAdapterTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    SearchStructurePersistenceAdapter searchStructurePersistenceAdapter;
    @Autowired
    SearchPersistenceAdapter searchPersistenceAdapter;
    @Autowired
    StructurePersistenceAdapter structurePersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    
    // data value
    final static String MEMBER_NAME = "sshyu";
    final static String SEARCH_TITLE = "산본역 2025 임장";
    final static String SEARCH_REGION = "경기도 군포시";
    final static String SEARCH_DESCRIPTION = "깔끔한 상권 + 여러 초등학교 존재";
    final static String STTURTURE_NAME = "대림솔거아파트";
    final static String STRUCTURE_ADDRESS = "경기 군포시 산본동 1146";
    final static String SEARCH_STRUCTURE_DESCRIPTION = "역세권, 대단지, 초등학교 근처!";
    // assert valus
    final static LocalDateTime TIME = LocalDateTime.now();
    final static LocalDateTime PLUS_TIME = TIME.plusMinutes(1);
    final static LocalDateTime MINUS_TIME = TIME.minusMinutes(1);

    UUID memberId;
    UUID searchId;
    Long structureId;
    Long structure2Id;
    UUID searchStructureId;
    
    @BeforeEach
    void 기초데이터생성() {

        memberId = memberPersistenceAdapter.save(
            Member.builder().name(MEMBER_NAME).build()
        );
        
        searchId = searchPersistenceAdapter.save(
            Search.builder()
                .member(Member.onlyId(memberId))
                .title(SEARCH_TITLE)
                .region(SEARCH_REGION)
                .description(SEARCH_DESCRIPTION)
                .build()
        );

        structureId = structurePersistenceAdapter.save(
            Structure.builder()
                .name(STTURTURE_NAME)
                .numberAddress(STRUCTURE_ADDRESS)
                .latitude(null)
                .longitude(null)
                .builtYear(1992)
                .createdAt(TIME)
                .updatedAt(TIME)
                .isDeleted(0)
                .build()
        );

        structure2Id = structurePersistenceAdapter.save(
            StructureFixture.validStructure2WithoutId()
        );

        searchStructureId = searchStructurePersistenceAdapter.save(
            SearchStructure.builder()
                .search(Search.onlyId(searchId))
                .structure(Structure.onlyId(structureId))
                .description(SEARCH_STRUCTURE_DESCRIPTION)
                .build()
        );
    }

    @Test
    void save_새로운데이터_저장() {
        em.flush();
        em.clear();

        SearchStructure searchStructure = searchStructurePersistenceAdapter.findBySearchStructureId(searchStructureId);

        assertThat(searchStructure.getDescription()).isEqualTo(SEARCH_STRUCTURE_DESCRIPTION);
        assertThat(searchStructure.getCreatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(searchStructure.getUpdatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(searchStructure.getIsDeleted()).isEqualTo(0);
    }

    @Test
    void softDelete_정상요청() {
        // when
        searchStructurePersistenceAdapter.softDeleteBySearchStructureId(searchStructureId);

        em.flush();
        em.clear();

        // then
        assertThrows(SearchStructureNotFoundException.class, () -> 
            searchStructurePersistenceAdapter.findBySearchStructureId(searchStructureId)
        );
    }

    @Test
    void update_정상요청() {
        // given
        UUID newSearchId = UUID.randomUUID();
        String newDescription = "새로운 설명";
        SearchStructure searchStructure = SearchStructure.ofBasic(
            searchStructureId, Search.onlyId(newSearchId), Structure.onlyId(structure2Id), newDescription
        );

        // when
        searchStructurePersistenceAdapter.updateBySearchStructure(searchStructure);
        
        em.flush();
        em.clear();

        SearchStructure updatedSearchStructure = searchStructurePersistenceAdapter.findBySearchStructureId(searchStructureId);

        // then
        assertThat(updatedSearchStructure.getSearch().getSearchId()).isEqualTo(searchId); // 변경되지 않는 항목
        assertThat(updatedSearchStructure.getStructure().getStructureId()).isEqualTo(structure2Id);  // 변경되는 항목
        assertThat(updatedSearchStructure.getDescription()).isEqualTo(newDescription);  // 변경되는 항목
        assertThat(updatedSearchStructure.getUpdatedAt()).isAfter(updatedSearchStructure.getCreatedAt());
        assertThat(updatedSearchStructure.getIsDeleted()).isEqualTo(0);
    }

    @Test
    void update_존재하지_않는_데이터_수정_시도시_예외_발생() {
        // given
        UUID randomId = UUID.randomUUID();
        SearchStructure searchStructure = SearchStructure.ofBasic(
            randomId, Search.onlyId(searchId), Structure.onlyId(structure2Id), "test"
        );

        // when/then
        assertThrows(SearchStructureNotFoundException.class, () -> 
            searchStructurePersistenceAdapter.updateBySearchStructure(searchStructure)
        );
    }

}
