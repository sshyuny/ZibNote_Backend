package com.sshyu.zibnote.adapter.out.persistence.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.note.NoteFieldPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnote.domain.search.model.EvalType;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.structure.model.Structure;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({SearchStructureNotePersistenceAdapter.class, SearchStructurePersistenceAdapter.class, SearchPersistenceAdapter.class, 
    StructurePersistenceAdapter.class, NoteFieldPersistenceAdapter.class, MemberPersistenceAdapter.class})
@ActiveProfiles("test")
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
    

    // data value
    final static String MEMBER_NAME = "sshyu";
    final static String NOTE_FIELD_NAME = "놀이터";
    final static String NOTE_FIELD_DESCRIPTION = "놀이터 상태";
    final static String SEARCH_TITLE = "산본역 2025 임장";
    final static String SEARCH_REGION = "경기도 군포시";
    final static String SEARCH_DESCRIPTION = "깔끔한 상권 + 여러 초등학교 존재";
    final static String STTURTURE_NAME = "대림솔거아파트";
    final static String STRUCTURE_ADDRESS = "경기 군포시 산본동 1146";
    final static String SEARCH_STRUCTURE_DESCRIPTION = "역세권, 대단지, 초등학교 근처!";
    final static EvalType SEARCH_STRUCTURE_NOTE_EVAL_TYPE = EvalType.STAR;
    final static String SEARCH_STRUCTURE_NOTE_EVAL_VALUE = "4";
    final static String SEARCH_STRUCTURE_NOTE_NOTE = "넓은 놀이터가 여러 개 있음";
    // assert valus
    final static LocalDateTime TIME = LocalDateTime.now();
    final static LocalDateTime PLUS_TIME = TIME.plusMinutes(1);
    final static LocalDateTime MINUS_TIME = TIME.minusMinutes(1);

    Long memberId;
    Long searchId;
    Long structureId;
    Long searchStructureId;
    Long noteFieldId;
    Long searchStructureNoteid;


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
        
        searchStructureId = searchStructurePersistenceAdapter.save(
            SearchStructure.builder()
                .search(Search.onlyId(searchId))
                .structure(Structure.onlyId(structureId))
                .description(SEARCH_STRUCTURE_DESCRIPTION)
                .build()
        );
            
        noteFieldId = noteFieldPersistenceAdapter.save(
            NoteField.builder()
                .member(Member.onlyId(memberId))
                .name(NOTE_FIELD_NAME)
                .description(NOTE_FIELD_DESCRIPTION)
                .build()
        );

        searchStructureNoteid = searchStructureNotePersistenceAdapter.save(
            SearchStructureNote.builder()
                .searchStructure(SearchStructure.onlyId(searchStructureId))
                .noteField(NoteField.onlyId(noteFieldId))
                .evalType(SEARCH_STRUCTURE_NOTE_EVAL_TYPE)
                .evalValue(SEARCH_STRUCTURE_NOTE_EVAL_VALUE)
                .note(SEARCH_STRUCTURE_NOTE_NOTE)
                .build()
        );
    }

    @Test
    void save_새로운데이터_저장() {
        em.flush();
        em.clear();

        SearchStructureNote searchStructureNote = searchStructureNotePersistenceAdapter.findBySearchStructureNoteId(searchStructureNoteid);

        assertThat(searchStructureNote.getSearchStructure().getSearchStructureId()).isEqualTo(searchStructureId);
        assertThat(searchStructureNote.getNoteField().getNoteFieldId()).isEqualTo(noteFieldId);
        assertThat(searchStructureNote.getEvalType()).isEqualTo(SEARCH_STRUCTURE_NOTE_EVAL_TYPE);
        assertThat(searchStructureNote.getEvalValue()).isEqualTo(SEARCH_STRUCTURE_NOTE_EVAL_VALUE);
        assertThat(searchStructureNote.getNote()).isEqualTo(SEARCH_STRUCTURE_NOTE_NOTE);
        assertThat(searchStructureNote.getCreatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(searchStructureNote.getUpdatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(searchStructureNote.getIsDeleted()).isEqualTo(0);
    }

    @Test
    void softDelete_정상요청() {
        // when
        searchStructureNotePersistenceAdapter.softDeleteBySearchStructureNoteId(searchStructureNoteid);

        em.flush();
        em.clear();

        // then
        assertThrows(SearchStructureNoteNotFoundException.class, () -> 
            searchStructureNotePersistenceAdapter.findBySearchStructureNoteId(searchStructureNoteid)
        );
    }

}
