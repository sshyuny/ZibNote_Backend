package com.sshyu.zibnote.application.service.member;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;
import com.sshyu.zibnote.domain.member.port.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberUseCase {

    private final MemberRepository memberRepository;

    @Override
    public void register(Member member) {
        memberRepository.save(member);        
    }

    @Override
    public Member findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findByMemberId(memberId);
    }

}
