package com.sshyu.zibnote.adapter.out.persistence.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.note.NoteFieldPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.model.EvalType;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.structure.model.Structure;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({SearchStructureNotePersistenceAdapter.class, SearchStructurePersistenceAdapter.class, SearchPersistenceAdapter.class, 
    StructurePersistenceAdapter.class, NoteFieldPersistenceAdapter.class, MemberPersistenceAdapter.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SearchStructureNotePersistenceAdapterTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    SearchStructureNotePersistenceAdapter searchStructureNotePersistenceAdapter;
    @Autowired
    SearchStructurePersistenceAdapter searchStructurePersistenceAdapter;
    @Autowired
    SearchPersistenceAdapter searchPersistenceAdapter;
    @Autowired
    StructurePersistenceAdapter structurePersistenceAdapter;
    @Autowired
    NoteFieldPersistenceAdapter noteFieldPersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    final String memberName = "sshyu";
    final LocalDateTime now = LocalDateTime.now();
    final String noteFieldName = "놀이터";
    final String noteFieldDescription = "놀이터 상태";
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
                .address(structureAddress)
                .latitude(null)
                .longitude(null)
                .builtYear(1992)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );

        Search search = searchPersistenceAdapter.findAllByMemberId(memberId).get(0);
        Structure structure = structurePersistenceAdapter.findByAddress(structureAddress);

        searchStructurePersistenceAdapter.save(
            SearchStructure.builder()
                .search(search)
                .structure(structure)
                .description(searchStructureDescription)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );

        noteFieldPersistenceAdapter.save(
            NoteField.builder()
                .member(Member.builder().memberId(memberId).build())
                .name(noteFieldName)
                .description(noteFieldDescription)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );

        SearchStructure searchStructure = searchStructurePersistenceAdapter.findAllBySearchId(search.getSearchId()).get(0);
        NoteField noteField = noteFieldPersistenceAdapter.findByMemberAndName(memberId, noteFieldName);
        
        searchStructureNotePersistenceAdapter.save(
            SearchStructureNote.builder()
                .searchStructure(searchStructure)
                .noteField(noteField)
                .evalType(EvalType.STAR)
                .evalValue("4")
                .note("넓은 놀이터가 여러 개 있음")
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );
    }

    @Test
    void softDelete_테스트() {

        // given
        Long memberId = memberPersistenceAdapter.findByName(memberName).getMemberId();
        Search search = searchPersistenceAdapter.findAllByMemberId(memberId).get(0);
        SearchStructure searchStructure = searchStructurePersistenceAdapter.findAllBySearchId(search.getSearchId()).get(0);

        SearchStructureNote searchStructureNoteBeforeDeleting = searchStructureNotePersistenceAdapter.findAllBySearchStructureId(searchStructure.getSearchStructureId()).get(0);
        assertEquals("4", searchStructureNoteBeforeDeleting.getEvalValue());
        assertEquals(0, searchStructureNoteBeforeDeleting.getIsDeleted());

        // when
        searchStructureNotePersistenceAdapter.softDeleteBySearchStructureNoteId(searchStructureNoteBeforeDeleting.getSearchStructureNoteId(), now);

        em.flush();
        em.clear();

        // then
        SearchStructureNote searchStructureNoteAfterDeleting = searchStructureNotePersistenceAdapter.findAllBySearchStructureId(searchStructure.getSearchStructureId()).get(0);
        assertEquals(1, searchStructureNoteAfterDeleting.getIsDeleted());
    }

}
