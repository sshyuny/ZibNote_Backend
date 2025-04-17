package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;

public interface SearchStructureJpaRepository extends JpaRepository<SearchStructureEntity, Long> {
    
    List<SearchStructureEntity> findAllBySearchEntity(SearchEntity searchEntity);

    @Modifying
    @Query("UPDATE SearchStructureEntity s SET s.isDeleted = 1, s.updatedAt = :updatedAt WHERE s.searchStructureId = :searchStructureId")
    void softDeleteBySearchStructureId(@Param("searchStructureId") Long searchStructureId, @Param("updatedAt") LocalDateTime updatedAt);

}
