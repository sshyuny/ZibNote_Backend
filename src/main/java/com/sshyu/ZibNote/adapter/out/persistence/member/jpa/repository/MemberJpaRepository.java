package com.sshyu.zibnote.adapter.out.persistence.member.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    
    Optional<MemberEntity> findByName(String name);
    
}
