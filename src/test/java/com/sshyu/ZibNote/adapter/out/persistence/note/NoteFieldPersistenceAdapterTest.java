package com.sshyu.zibnote.adapter.out.persistence.note;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.note.model.NoteField;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({NoteFieldPersistenceAdapter.class, MemberPersistenceAdapter.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NoteFieldPersistenceAdapterTest {

    @Autowired
    EntityManager em;
    @Autowired
    NoteFieldPersistenceAdapter noteFieldPersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    final String memberName = "sshyu";
    final String noteFieldName = "놀이터";
    final String noteFieldDescription = "놀이터 상태";

    @BeforeEach
    void 기본데이터생성() {

        Member member = Member.builder().name(memberName).build();
        memberPersistenceAdapter.save(member);

        Long memberId = memberPersistenceAdapter.findByName(memberName).getMemberId();

        noteFieldPersistenceAdapter.save(
            NoteField.builder()
                .member(Member.builder().memberId(memberId).build())
                .name(noteFieldName)
                .description(noteFieldDescription)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(0)
                .build()
        );
    }

    @Test
    void softDelete_테스트() {

        // given
        Long memberId = memberPersistenceAdapter.findByName(memberName).getMemberId();

        NoteField noteFieldBeforeDeleting = noteFieldPersistenceAdapter.findByMemberAndName(memberId, noteFieldName);
        assertEquals(0, noteFieldBeforeDeleting.getIsDeleted());
        
        // when
        noteFieldPersistenceAdapter.softDeleteByNoteFieldId(noteFieldBeforeDeleting.getNoteFieldId(), LocalDateTime.now());

        em.flush();
        em.clear();
        
        // then
        NoteField noteFieldAfterDeleting = noteFieldPersistenceAdapter.findByMemberAndName(memberId, noteFieldName);
        assertEquals(1, noteFieldAfterDeleting.getIsDeleted());
    }
    
}
