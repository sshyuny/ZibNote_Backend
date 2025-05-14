package com.sshyu.zibnote.domain.member.port.in;

import com.sshyu.zibnote.domain.member.model.Member;

public interface MemberUseCase {
    
    Long register(Member member);

    Member findByName(String name);

    Member getMember(Long memberId);
    
}
