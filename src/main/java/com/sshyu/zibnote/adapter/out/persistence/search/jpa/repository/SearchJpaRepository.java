package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;

public interface SearchJpaRepository extends JpaRepository<SearchEntity, UUID> {
    
    List<SearchEntity> findAllByMemberEntity(MemberEntity memberEntity);

}
