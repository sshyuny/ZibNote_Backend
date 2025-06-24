package com.sshyu.zibnotes.adapter.out.persistence.member.jpa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnotes.adapter.out.persistence.member.jpa.entity.MemberEntity;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, UUID> {
    
    Optional<MemberEntity> findByName(String name);
    
}
