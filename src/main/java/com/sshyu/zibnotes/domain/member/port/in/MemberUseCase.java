package com.sshyu.zibnotes.domain.member.port.in;

import java.util.UUID;

import com.sshyu.zibnotes.domain.member.model.Member;

public interface MemberUseCase {
    
    UUID register(Member member);

    Member findByName(String name);

    Member getMember(UUID memberId);
    
}
