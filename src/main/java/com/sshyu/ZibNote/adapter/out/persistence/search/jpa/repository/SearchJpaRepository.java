package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sshyu.zibnote.adapter.out.persistence.member.jpa.entity.MemberEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;

public interface SearchJpaRepository extends JpaRepository<SearchEntity, Long> {
    
    Optional<SearchEntity> findBySearchId(Long searchId);

    List<SearchEntity> findAllByMemberEntityAndIsDeleted(MemberEntity memberEntity, int isDeleted);

    @Modifying
    @Query("UPDATE SearchEntity s SET s.isDeleted = 1, s.updatedAt = :updatedAt WHERE s.searchId = :searchId")
    void softDeleteBySearchId(@Param("searchId") Long searchId, @Param("updatedAt") LocalDateTime updatedAt);

}
