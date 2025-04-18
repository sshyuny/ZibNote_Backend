package com.sshyu.zibnote.domain.member.port.in;

import com.sshyu.zibnote.domain.member.model.Member;

public interface MemberUseCase {
    
    void register(Member member);

    Member findByName(String name);
    
}
