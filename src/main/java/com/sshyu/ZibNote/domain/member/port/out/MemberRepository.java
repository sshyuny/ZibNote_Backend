package com.sshyu.zibnote.domain.member.port.out;

import com.sshyu.zibnote.domain.member.model.Member;

public interface MemberRepository {
    
    void save(Member member);

    Member findByMemberId(Long memberId);

    Member findByName(String loginId);
    
}
