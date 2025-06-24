package com.sshyu.zibnotes.adapter.out.persistence.member.mapper;

import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnotes.domain.member.model.Member;

public class MemberEntityMapper {
    
    public static Member toDomain(final MemberEntity entity) {

        if (entity == null) { return Member.builder().build(); }

        return Member.builder()
                    .memberId(entity.getMemberId())
                    .name(entity.getName())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }

    public static MemberEntity toEntity(final Member domain) {

        if (domain == null) { return MemberEntity.builder().build(); }
        
        return MemberEntity.builder()
                    .memberId(domain.getMemberId())
                    .name(domain.getName())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }
    
}
