package com.sshyu.zibnote.adapter.out.persistence.member;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.member.jpa.repository.MemberJpaRepository;
import com.sshyu.zibnote.domain.member.exception.MemberNotFoundException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(Member member) {
        LocalDateTime now = LocalDateTime.now();
        
        memberJpaRepository.save(
            MemberEntity.builder()
                .name(member.getName())
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );
    }

    @Override
    public Member findByMemberId(Long memberId) {

        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException());

        return Member.builder()
                .memberId(memberEntity.getMemberId())
                .name(memberEntity.getName())
                .createdAt(memberEntity.getCreatedAt())
                .updatedAt(memberEntity.getUpdatedAt())
                .isDeleted(memberEntity.getIsDeleted())
                .build();
    }

    @Override
    public Member findByName(String loginId) {

        MemberEntity memberEntity = memberJpaRepository.findByName(loginId)
            .orElseThrow(() -> new MemberNotFoundException());

        return Member.builder()
                .memberId(memberEntity.getMemberId())
                .name(memberEntity.getName())
                .createdAt(memberEntity.getCreatedAt())
                .updatedAt(memberEntity.getUpdatedAt())
                .isDeleted(memberEntity.getIsDeleted())
                .build();
    }

}
