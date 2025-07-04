package com.sshyu.zibnote.application.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

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
import com.sshyu.zibnote.application.service.search.SearchStructureNoteService;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNoteNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.fixture.MemberFixture;
import com.sshyu.zibnote.fixture.NoteFieldFixture;
import com.sshyu.zibnote.fixture.SearchFixture;
import com.sshyu.zibnote.fixture.SearchStructureNoteFixture;
import com.sshyu.zibnote.fixture.StructureFixture;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class SearchStructureNoteServiceIntegrationTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    SearchStructureNoteService sut;
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

    UUID memberId;
    UUID searchId;
    Long structureId1;
    Long structureId2;
    UUID searchStructureId1;
    UUID searchStructureId2;
    Long noteFieldId1;
    Long noteFieldId2;
    SearchStructureNote noteWithSearchStructure1AndNoteField1;
    SearchStructureNote noteWithSearchStructure1AndNoteField2;
    SearchStructureNote noteWithSearchStructure2AndNoteField1;
    SearchStructureNote noteWithSearchStructure2AndNoteField2;

    @BeforeEach
    void setUp() {
        
        memberId = memberPersistenceAdapter.save(Member.ofBasic(null, MemberFixture.MEMBER_A_NAME));

        searchId = searchPersistenceAdapter.save(Search.ofBasic(null, Member.onlyId(memberId), SearchFixture.SEARCH_1_TITLE, SearchFixture.SEARCH_1_REGION, null));

        structureId1 = structurePersistenceAdapter.save(StructureFixture.validStructure1WithoutId());
        structureId2 = structurePersistenceAdapter.save(StructureFixture.validStructure2WithoutId());
        
        searchStructureId1 = searchStructurePersistenceAdapter.save(SearchStructure.ofBasic(null, Search.onlyId(searchId), Structure.onlyId(structureId1), null));
        searchStructureId2 = searchStructurePersistenceAdapter.save(SearchStructure.ofBasic(null, Search.onlyId(searchId), Structure.onlyId(structureId2), null));

        noteFieldId1 = noteFieldPersistenceAdapter.save(NoteField.ofBasic(null, Member.onlyId(
            memberId), NoteFieldFixture.NOTE_FIELD_1_NAME, null));
        noteFieldId2 = noteFieldPersistenceAdapter.save(NoteField.ofBasic(null, Member.onlyId(memberId), NoteFieldFixture.NOTE_FILED_2_NAME, null));

        noteWithSearchStructure1AndNoteField1 = SearchStructureNoteFixture.createNote(null, searchStructureId1, noteFieldId1);
        noteWithSearchStructure1AndNoteField2 = SearchStructureNoteFixture.createNote(null, searchStructureId1, noteFieldId2);
        noteWithSearchStructure2AndNoteField1 = SearchStructureNoteFixture.createNote(null, searchStructureId2, noteFieldId1);
        noteWithSearchStructure2AndNoteField2 = SearchStructureNoteFixture.createNote(null, searchStructureId2, noteFieldId2);
    }

    @Test
    void registerSearchStructureNote_정상_등록() {

        UUID noteId = sut.registerSearchStructureNote(noteWithSearchStructure1AndNoteField1, memberId);

        SearchStructureNote selectedNote = searchStructureNotePersistenceAdapter.findBySearchStructureNoteId(noteId);

        assertThat(selectedNote.getSearchStructureNoteId()).isEqualTo(noteId);
    }

    @Test
    void registerSearchStructureNote_잘못된_계정_접근시_예외_발생() {

        UUID unauthMemberId = UUID.randomUUID();

        assertThrows(UnauthorizedAccessException.class, () ->
            sut.registerSearchStructureNote(noteWithSearchStructure1AndNoteField1, unauthMemberId));
    }

    @Test
    void softDeleteSearchStructureNote_정상_삭제() {

        UUID savedNoteId = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);

        sut.softDeleteSearchStructureNote(savedNoteId, memberId);

        em.flush();
        em.clear();

        assertThrows(SearchStructureNoteNotFoundException.class, () ->
            searchStructureNotePersistenceAdapter.findBySearchStructureNoteId(savedNoteId));
    }

    @Test
    void softDeleteSearchStructureNote_잘못된_계정_접근시_예외_발생() {

        UUID unauthMemberId = UUID.randomUUID();
        UUID savedNoteId = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);

        assertThrows(UnauthorizedAccessException.class, () -> 
            sut.softDeleteSearchStructureNote(savedNoteId, unauthMemberId));
    }

    @Test
    void listSearchStructureNotesBySearchStructure_정상_조회() {

        UUID savedNoteId1 = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);
        UUID savedNoteId2 = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField2);
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure2AndNoteField1);
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure2AndNoteField2);

        List<SearchStructureNote> list = sut.listSearchStructureNotesBySearchStructure(searchStructureId1, memberId);

        assertThat(list).hasSize(2)
            .extracting("searchStructureNoteId")
            .containsExactly(savedNoteId1, savedNoteId2);
    }

    @Test
    void listSearchStructureNotesBySearchStructure_잘못된_계정_접근시_예외_발생() {

        UUID unauthMemberId = UUID.randomUUID();
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);
        searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField2);

        assertThrows(UnauthorizedAccessException.class, () ->
            sut.listSearchStructureNotesBySearchStructure(searchStructureId1, unauthMemberId));
    }

    @Test
    void updateSearchStructureNote_정상_수정() {
        // given
        UUID noteId = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);
        SearchStructureNote newNote = SearchStructureNoteFixture.createNote(noteId, null, noteFieldId2);

        // when
        sut.updateSearchStructureNote(newNote, memberId);
        SearchStructureNote selectedNote = searchStructureNotePersistenceAdapter.findBySearchStructureNoteId(noteId);

        // then
        assertThat(selectedNote.getNoteField().getNoteFieldId()).isEqualTo(noteFieldId2);
    }

    @Test
    void updateSearchStructureNote_비인가_사용자_접근시_예외_발생() {
        // given
        UUID unauthMemberId = UUID.randomUUID();
        UUID noteId = searchStructureNotePersistenceAdapter.save(noteWithSearchStructure1AndNoteField1);
        SearchStructureNote newNote = SearchStructureNoteFixture.createNote(noteId, null, noteFieldId2);

        // when/then
        assertThrows(UnauthorizedAccessException.class, () ->
            sut.updateSearchStructureNote(newNote, unauthMemberId));
    }

}