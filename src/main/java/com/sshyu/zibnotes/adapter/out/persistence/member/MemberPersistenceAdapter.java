package com.sshyu.zibnotes.adapter.out.persistence.member;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.repository.MemberJpaRepository;
import com.sshyu.zibnotes.adapter.out.persistence.member.mapper.MemberEntityMapper;
import com.sshyu.zibnotes.domain.member.exception.MemberNotFoundException;
import com.sshyu.zibnotes.domain.member.model.Member;
import com.sshyu.zibnotes.domain.member.port.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UUID save(final Member member) {
        
        final MemberEntity memberEntity = MemberEntity.builder()
            .name(member.getName())
            .build();

        memberJpaRepository.save(memberEntity);

        return memberEntity.getMemberId();
    }

    @Override
    public Member findByMemberId(final UUID memberId) {

        final MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException());

        return MemberEntityMapper.toDomain(memberEntity);
    }

    @Override
    public Member findByName(final String loginId) {

        final MemberEntity memberEntity = memberJpaRepository.findByName(loginId)
            .orElseThrow(() -> new MemberNotFoundException());

        return MemberEntityMapper.toDomain(memberEntity);
    }

}
