package com.sshyu.zibnotes.domain.member.port.out;

import java.util.UUID;

import com.sshyu.zibnotes.domain.member.model.Member;

public interface MemberRepository {
    
    UUID save(Member member);

    Member findByMemberId(UUID memberId);

    Member findByName(String loginId);
    
}
