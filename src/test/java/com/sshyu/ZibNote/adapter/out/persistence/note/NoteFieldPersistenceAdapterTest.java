package com.sshyu.zibnote.adapter.out.persistence.note;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.exception.NoteFieldNotFoundException;
import com.sshyu.zibnote.domain.note.model.NoteField;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({NoteFieldPersistenceAdapter.class, MemberPersistenceAdapter.class})
@ActiveProfiles("test")
public class NoteFieldPersistenceAdapterTest {

    @Autowired
    EntityManager em;
    @Autowired
    NoteFieldPersistenceAdapter noteFieldPersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    // data value
    final static String MEMBER_NAME = "sshyu";
    final static String NOTE_FIELD_NAME = "놀이터";
    final static String NOTE_FIELD_DESCRIPTION = "놀이터 상태";
    // assert valus
    final static LocalDateTime TIME = LocalDateTime.now();
    final static LocalDateTime PLUS_TIME = TIME.plusMinutes(1);
    final static LocalDateTime MINUS_TIME = TIME.minusMinutes(1);

    Long memberId;
    Long noteFieldId;

    @BeforeEach
    void 기본데이터_생성() {

        memberId = memberPersistenceAdapter.save(
            Member.builder().name(MEMBER_NAME).build()
        );

        noteFieldId = noteFieldPersistenceAdapter.save(
            NoteField.builder()
                .member(Member.onlyId(memberId))
                .name(NOTE_FIELD_NAME)
                .description(NOTE_FIELD_DESCRIPTION)
                .build()
        );
    }

    @Test
    void save_새로운데이터_저장() {
        em.flush();
        em.clear();

        NoteField noteField = noteFieldPersistenceAdapter.findByNoteFieldId(noteFieldId);

        assertThat(noteField.getMember().getMemberId()).isEqualTo(memberId);
        assertThat(noteField.getName()).isEqualTo(NOTE_FIELD_NAME);
        assertThat(noteField.getDescription()).isEqualTo(NOTE_FIELD_DESCRIPTION);
        assertThat(noteField.getCreatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(noteField.getUpdatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(noteField.getIsDeleted()).isEqualTo(0);
    }

    @Test
    void softDelete_정상요청() {
        // when
        noteFieldPersistenceAdapter.softDeleteByNoteFieldId(noteFieldId);

        em.flush();
        em.clear();
        
        // then
        assertThrows(NoteFieldNotFoundException.class, () -> 
            noteFieldPersistenceAdapter.findByNoteFieldId(noteFieldId)
        );
    }
    
}
