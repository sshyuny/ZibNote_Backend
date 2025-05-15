package com.sshyu.zibnote.application.service.note;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.note.NoteFieldPersistenceAdapter;
import com.sshyu.zibnote.domain.note.exception.NoteFieldNotFoundException;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.fixture.MemberFixture;
import com.sshyu.zibnote.fixture.NoteFieldFixture;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class NoteFieldServiceIntegrationTest {
    
    @Autowired
    NoteFieldService sut;
    @Autowired
    EntityManager em;
    @Autowired
    NoteFieldPersistenceAdapter noteFieldPersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    Long memberAId;
    Long memberBId;
    Long noteField1Id;
    Long noteField2Id;

    @BeforeEach
    void setUp() {
        memberAId = memberPersistenceAdapter.save(MemberFixture.of(null, MemberFixture.NAME_A));
        memberBId = memberPersistenceAdapter.save(MemberFixture.of(null, MemberFixture.NAME_B));

        NoteField noteField1 = NoteFieldFixture.of(null, memberAId, NoteFieldFixture.NAME_1);
        NoteField noteField2 = NoteFieldFixture.of(null, memberAId, NoteFieldFixture.NAME_2);
        noteField1Id = noteFieldPersistenceAdapter.save(noteField1);
        noteField2Id = noteFieldPersistenceAdapter.save(noteField2);
    }

    @Test
    void registerNoteField_정상_상황() {
        //given
        NoteField noteField = NoteFieldFixture.of(null, memberAId, NoteFieldFixture.NAME_1);

        //when
        Long newNoteFieldId = sut.registerNoteField(noteField);

        em.flush();
        em.clear();

        //then
        NoteField selectedNoteFiled = noteFieldPersistenceAdapter.findByNoteFieldId(newNoteFieldId);
        assertThat(selectedNoteFiled.getMember().getMemberId()).isEqualTo(memberAId);
        assertThat(selectedNoteFiled.getName()).isEqualTo(NoteFieldFixture.NAME_1);
    }

    @Test
    void listNoteFieldsByMember_리스트_반환() {

        assertThat(sut.listNoteFieldsByMember(memberAId))
            .hasSize(2);
    }

    @Test
    void listNoteFieldsByMember_빈_리스트_반환() {

        assertThat(sut.listNoteFieldsByMember(memberBId))
            .hasSize(0);
    }

    @Test
    void softDeleteNoteField_정상_삭제() {

        sut.softDeleteNoteField(noteField1Id, memberAId);

        em.flush();
        em.clear();

        assertThrows(NoteFieldNotFoundException.class, () ->
            noteFieldPersistenceAdapter.findByNoteFieldId(noteField1Id));
    }

    @Test
    void softDeleteNoteField_존재하지_않는_ID로_시도시_예외_발생() {

        assertThrows(NoteFieldNotFoundException.class, () ->
            sut.softDeleteNoteField(4455L, memberAId));
    }

}
