package com.sshyu.zibnote.adapter.out.persistence.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.exception.NotValidSearchStructureException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.structure.model.Structure;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({SearchStructurePersistenceAdapter.class, SearchPersistenceAdapter.class, MemberPersistenceAdapter.class, StructurePersistenceAdapter.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    final String memberName = "sshyu";
    final LocalDateTime now = LocalDateTime.now();
    final String searchTitle = "산본역 2025 임장";
    final String structureName = "목화아파트";
    final String structureAddress = "경기 군포시 금정동 850";
    final String searchStructureDescription = "역세권 + 상권! 관심 있음!";
    
    @BeforeEach
    void 기초데이터생성() {

        Member member = Member.builder().name(memberName).build();
        memberPersistenceAdapter.save(member);

        Long memberId = memberPersistenceAdapter.findByName(memberName).getMemberId();
        
        searchPersistenceAdapter.save(
            Search.builder()
                .member(Member.builder().memberId(memberId).build())
                .title(searchTitle)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );

        structurePersistenceAdapter.save(
            Structure.builder()
                .name(structureName)
                .numberAddress(structureAddress)
                .latitude(null)
                .longitude(null)
                .builtYear(1992)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );
    }

    @Test
    void 비정상데이터_structure없이_저장() {

        // given
        Long memberId = memberPersistenceAdapter.findByName(memberName).getMemberId();
        Search search = searchPersistenceAdapter.findAllByMemberId(memberId).get(0);

        // then
        assertThrows(NotValidSearchStructureException.class, () -> 
            searchStructurePersistenceAdapter.save(
                SearchStructure.builder()
                    .search(search)
                    .structure(null)
                    .description(searchStructureDescription)
                    .createdAt(now)
                    .updatedAt(now)
                    .isDeleted(0)
                    .build()
            )
        );
    }

    @Test
    void softDelete_테스트() {

        // given
        Long memberId = memberPersistenceAdapter.findByName(memberName).getMemberId();
        Search search = searchPersistenceAdapter.findAllByMemberId(memberId).get(0);
        List<Structure> structures = structurePersistenceAdapter.findByAddressContaining(structureAddress);

        searchStructurePersistenceAdapter.save(
            SearchStructure.builder()
                .search(search)
                .structure(structures.get(0))
                .description(searchStructureDescription)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );

        SearchStructure searchStructureBeforeDeleting = searchStructurePersistenceAdapter.findAllBySearchId(search.getSearchId()).get(0);
        assertEquals(0, searchStructureBeforeDeleting.getIsDeleted());
        
        // when
        searchStructurePersistenceAdapter.softDeleteBySearchStructureId(searchStructureBeforeDeleting.getSearchStructureId(), now);

        em.flush();
        em.clear();

        // then
        SearchStructure searchStructureAfterDeleting = searchStructurePersistenceAdapter.findBySearchStructureId(searchStructureBeforeDeleting.getSearchStructureId());
        assertEquals(1, searchStructureAfterDeleting.getIsDeleted());
    }
    
}
