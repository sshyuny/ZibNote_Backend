package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;

public interface SearchJpaRepository extends JpaRepository<SearchEntity, Long> {
    
    List<SearchEntity> findAllByMemberEntityAndIsDeleted(MemberEntity memberEntity, int isDeleted);

}
