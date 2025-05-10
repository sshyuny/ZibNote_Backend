package com.sshyu.zibnote.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.note.NoteFieldPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.search.SearchPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.search.SearchStructureNotePersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.search.SearchStructurePersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.member.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.structure.model.Structure;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class SearchStructureNoteServiceIntegrationTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    SearchStructureNoteService searchStructureNoteService;
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

    final static String MEMBER_NAME = "sshyu";
    final static String NOTE_FIELD_NAME_1 = "놀이터";
    final static String NOTE_FIELD_NAME_2 = "상권과 거리";
    final static String SEARCH_TITLE = "산본역 2025 임장";
    final static String SEARCH_REGION = "경기도 군포시";
    final static String STTURTURE_NAME_1 = "대림솔거아파트";
    final static String STTURTURE_NAME_2 = "한양백두9단지아파트";
    final static String STRUCTURE_ADDRESS_1 = "경기 군포시 산본동 1146";
    final static String STRUCTURE_ADDRESS_2 = "경기 군포시 산본동 1119";

    Long memberId;
    Long searchId;
    Long structureId1;
    Long structureId2;
    Long searchStructureId1;
    Long searchStructureId2;
    Long noteFieldId1;
    Long noteFieldId2;
    SearchStructureNote noteWithSearchStructure1AndNoteField1;
    SearchStructureNote noteWithSearchStructure1AndNoteField2;
    SearchStructureNote noteWithSearchStructure2AndNoteField1;
    SearchStructureNote noteWithSearchStructure2AndNoteField2;

    @BeforeEach
    void setUp() {
        
        memberId = memberPersistenceAdapter.save(
            Member.builder().name(MEMBER_NAME).build()
        );

        searchId = searchPersistenceAdapter.save(
            Search.builder()
                .member(Member.builder().memberId(memberId).build())
                .title(SEARCH_TITLE)
                .region(SEARCH_REGION)
                .build()
        );

        structureId1 = structurePersistenceAdapter.save(
            Structure.builder()
                .name(STTURTURE_NAME_1)
                .numberAddress(STRUCTURE_ADDRESS_1)
                .builtYear(1992)
                .build()
        );
        structureId2 = structurePersistenceAdapter.save(
            Structure.builder()
                .name(STTURTURE_NAME_2)
                .numberAddress(STRUCTURE_ADDRESS_2)
                .builtYear(1994)
                .build()
        );
        
        searchStructureId1 = searchStructurePersistenceAdapter.save(
            SearchStructure.builder()
            .search(Search.builder().searchId(searchId).build())
            .structure(Structure.builder().structureId(structureId1).build())
            .build()
        );
        searchStructureId2 = searchStructurePersistenceAdapter.save(
            SearchStructure.builder()
            .search(Search.builder().searchId(searchId).build())
            .structure(Structure.builder().structureId(structureId2).build())
            .build()
        );

        noteFieldId1 = noteFieldPersistenceAdapter.save(
            NoteField.builder()
                .member(Member.builder().memberId(memberId).build())
                .name(NOTE_FIELD_NAME_1)
                .build()
        );
        noteFieldId2 = noteFieldPersistenceAdapter.save(
            NoteField.builder()
                .member(Member.builder().memberId(memberId).build())
                .name(NOTE_FIELD_NAME_2)
                .build()
        );

        noteWithSearchStructure1AndNoteField1 = SearchStructureNote.builder()
            .searchStructure(SearchStructure.builder().searchStructureId(searchStructureId1).build())
            .noteField(NoteField.builder().noteFieldId(noteFieldId1).build())
            .build();
        noteWithSearchStructure1AndNoteField2 = SearchStructureNote.builder()
            .searchStructure(SearchStructure.builder().searchStructureId(searchStructureId1).build())
            .noteField(NoteField.builder().noteFieldId(noteFieldId2).build())
            .build();
        noteWithSearchStructure2AndNoteField1 = SearchStructureNote.builder()
            .searchStructure(SearchStructure.builder().searchStructureId(searchStructureId2).build())
            .noteField(NoteField.builder().noteFieldId(noteFieldId1).build())
            .build();
        noteWithSearchStructure2AndNoteField2 = SearchStructureNote.builder()
            .searchStructure(SearchStructure.builder().searchStructureId(searchStructureId2).build())
            .noteField(NoteField.builder().noteFieldId(noteFieldId2).build())
            .build();
    }

    @Test
    void registerSearchStructureNote_정상_등록() {

        Long noteId = searchStructureNoteService.registerSearchStructureNote(noteWithSearchStructure1AndNoteField1, memberId);

        SearchStructureNote selectedNote = searchStructureNotePersistenceAdapter.findBySearchStructureNoteId(noteId);

        assertThat(selectedNote.getSearchStructureNoteId()).isEqualTo(noteId);
    }

    @Test
    void registerSearchStructureNote_잘못된_계정_접근시_예외_발생() {

        Long unauthMemberId = memberId + 1;

        assertThrows(UnauthorizedAccessException.class, () ->
            searchStructureNoteService.registerSearchStructureNote(noteWithSearchStructure1AndNoteField1, unauthMemberId));
    }

    @Test
    void softDeleteSearchStructureNote_정상_삭제() {

        Long savedNoteId = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);

        searchStructureNoteService.softDeleteSearchStructureNote(savedNoteId, memberId);

        em.flush();
        em.clear();

        assertThrows(SearchStructureNoteNotFoundException.class, () ->
            searchStructureNotePersistenceAdapter.findBySearchStructureNoteId(savedNoteId));
    }

    @Test
    void softDeleteSearchStructureNote_잘못된_계정_접근시_예외_발생() {

        Long unauthMemberId = memberId + 1;
        Long savedNoteId = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);

        assertThrows(UnauthorizedAccessException.class, () -> 
            searchStructureNoteService.softDeleteSearchStructureNote(savedNoteId, unauthMemberId));
    }

    @Test
    void listSearchStructureNotesBySearchStructure_정상_조회() {

        Long savedNoteId1 = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);
        Long savedNoteId2 = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField2);
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure2AndNoteField1);
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure2AndNoteField2);

        List<SearchStructureNote> list = searchStructureNoteService.listSearchStructureNotesBySearchStructure(searchStructureId1, memberId);

        assertThat(list).hasSize(2)
            .extracting("searchStructureNoteId")
            .containsExactly(savedNoteId1, savedNoteId2);
    }

    @Test
    void listSearchStructureNotesBySearchStructure_잘못된_계정_접근시_예외_발생() {

        Long unauthMemberId = memberId + 1;
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField2);

        assertThrows(UnauthorizedAccessException.class, () ->
            searchStructureNoteService.listSearchStructureNotesBySearchStructure(searchStructureId1, unauthMemberId));
    }

}