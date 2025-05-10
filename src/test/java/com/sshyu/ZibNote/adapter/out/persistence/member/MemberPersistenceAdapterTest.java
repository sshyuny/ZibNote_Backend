package com.sshyu.zibnote.adapter.out.persistence.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.domain.member.model.Member;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({MemberPersistenceAdapter.class})
@ActiveProfiles("test")
public class MemberPersistenceAdapterTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    // data value
    final static String MEMBER_NAME = "sshyu";
    // assert valus
    final static LocalDateTime TIME = LocalDateTime.now();
    final static LocalDateTime PLUS_TIME = TIME.plusMinutes(1);
    final static LocalDateTime MINUS_TIME = TIME.minusMinutes(1);

    Long memberId;

    @BeforeEach
    void 기초데이터생성() {
        Member member = Member.builder().name(MEMBER_NAME).build();
        memberId = memberPersistenceAdapter.save(member);
    }

    @Test
    void save_새로운데이터_저장() {
        
        Member member = memberPersistenceAdapter.findByMemberId(memberId);

        assertThat(member.getName()).isEqualTo(MEMBER_NAME);
        assertThat(member.getCreatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(member.getUpdatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(member.getIsDeleted()).isEqualTo(0);
    }

}
