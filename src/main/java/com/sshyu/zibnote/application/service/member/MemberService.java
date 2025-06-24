package com.sshyu.zibnote.application.service.member;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;
import com.sshyu.zibnote.domain.member.port.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberUseCase {

    private final MemberRepository memberRepository;

    /**
     * 사용자를 등록한다.
     * 
     * @param member 등록할 사용자
     */
    @Override
    public UUID register(final Member member) {

        return memberRepository.save(member);        
    }

    /**
     * 이름으로 사용자를 검색한다.
     * 
     * @param name 사용자 이름
     * @return 검색된 사용자
     */
    @Override
    public Member findByName(final String name) {

        return memberRepository.findByName(name);
    }

    /**
     * ID로 사용자를 조회한다.
     * 
     * @param memberId 조회 대상 ID
     * @return ID에 일치하는 Member
     */
    @Override
    public Member getMember(final UUID memberId) {
        
        return memberRepository.findByMemberId(memberId);
    }

}
