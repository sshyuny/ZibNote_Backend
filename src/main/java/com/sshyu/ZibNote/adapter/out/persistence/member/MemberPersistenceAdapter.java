package com.sshyu.zibnote.adapter.out.persistence.member;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.member.jpa.repository.MemberJpaRepository;
import com.sshyu.zibnote.adapter.out.persistence.member.mapper.MemberMapper;
import com.sshyu.zibnote.domain.member.exception.MemberNotFoundException;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Long save(Member member) {
        
        MemberEntity memberEntity = MemberEntity.builder()
            .name(member.getName())
            .build();

        memberJpaRepository.save(memberEntity);

        return memberEntity.getMemberId();
    }

    @Override
    public Member findByMemberId(Long memberId) {

        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException());

        return MemberMapper.toDomain(memberEntity);
    }

    @Override
    public Member findByName(String loginId) {

        MemberEntity memberEntity = memberJpaRepository.findByName(loginId)
            .orElseThrow(() -> new MemberNotFoundException());

        return MemberMapper.toDomain(memberEntity);
    }

}
