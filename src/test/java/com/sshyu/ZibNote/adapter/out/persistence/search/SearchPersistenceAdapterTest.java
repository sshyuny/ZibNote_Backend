package com.sshyu.zibnote.adapter.out.persistence.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.member.MemberPersistenceAdapter;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.search.exception.SearchNotFoundException;
import com.sshyu.zibnote.domain.search.model.Search;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({SearchPersistenceAdapter.class, MemberPersistenceAdapter.class})
@ActiveProfiles("test")
public class SearchPersistenceAdapterTest {
    
    @Autowired
    EntityManager em;
    @Autowired
    SearchPersistenceAdapter searchPersistenceAdapter;
    @Autowired
    MemberPersistenceAdapter memberPersistenceAdapter;

    // data value
    final static String MEMBER_NAME = "sshyu";
    final static String SEARCH_TITLE = "산본역 2025 임장";
    final static String SEARCH_REGION = "경기도 군포시";
    final static String SEARCH_DESCRIPTION = "깔끔한 상권 + 여러 초등학교 존재";
    // assert valus
    final static LocalDateTime TIME = LocalDateTime.now();
    final static LocalDateTime PLUS_TIME = TIME.plusMinutes(1);
    final static LocalDateTime MINUS_TIME = TIME.minusMinutes(1);

    UUID memberId;
    UUID searchId;

    @BeforeEach
    void 기초데이터_생성() {

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
    }

    @Test
    void save_새로운데이터_저장() {
        em.flush();
        em.clear();

        Search search = searchPersistenceAdapter.findBySearchId(searchId);

        assertThat(search.getTitle()).isEqualTo(SEARCH_TITLE);
        assertThat(search.getMember().getMemberId()).isEqualTo(memberId);
        assertThat(search.getCreatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(search.getUpdatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(search.getIsDeleted()).isEqualTo(0);
    }

    @Test
    void softDelete_정상요청() {
        // when
        searchPersistenceAdapter.softDeleteBySearchId(searchId);

        em.flush();
        em.clear();

        //then
        assertThrows(SearchNotFoundException.class, () ->
            searchPersistenceAdapter.findBySearchId(searchId)
        );
    }

}
