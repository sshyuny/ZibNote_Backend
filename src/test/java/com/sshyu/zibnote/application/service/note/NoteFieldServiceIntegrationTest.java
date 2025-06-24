package com.sshyu.zibnote.application.service.note;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.adapter.out.persistence.note.NoteFieldPersistenceAdapter;
import com.sshyu.zibnote.application.service.note.NoteFieldService;
import com.sshyu.zibnote.domain.member.model.Member;
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

    UUID memberAId;
    UUID memberBId;
    Long noteField1Id;
    Long noteField2Id;

    @BeforeEach
    void setUp() {
        memberAId = memberPersistenceAdapter.save(Member.ofBasic(null, MemberFixture.MEMBER_A_NAME));
        memberBId = memberPersistenceAdapter.save(Member.ofBasic(null, MemberFixture.MEMBER_B_NAME));

        NoteField noteField1 = NoteField.ofBasic(null, Member.onlyId(memberAId), NoteFieldFixture.NOTE_FIELD_1_NAME, null);
        NoteField noteField2 = NoteField.ofBasic(null, Member.onlyId(memberAId), NoteFieldFixture.NOTE_FILED_2_NAME, null);
        noteField1Id = noteFieldPersistenceAdapter.save(noteField1);
        noteField2Id = noteFieldPersistenceAdapter.save(noteField2);
    }

    @Test
    void registerNoteField_정상_상황() {
        //given
        NoteField noteField = NoteField.ofBasic(null, Member.onlyId(memberAId), NoteFieldFixture.NOTE_FIELD_1_NAME, null);

        //when
        Long newNoteFieldId = sut.registerNoteField(noteField);

        em.flush();
        em.clear();

        //then
        NoteField selectedNoteFiled = noteFieldPersistenceAdapter.findByNoteFieldId(newNoteFieldId);
        assertThat(selectedNoteFiled.getMember().getMemberId()).isEqualTo(memberAId);
        assertThat(selectedNoteFiled.getName()).isEqualTo(NoteFieldFixture.NOTE_FIELD_1_NAME);
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
