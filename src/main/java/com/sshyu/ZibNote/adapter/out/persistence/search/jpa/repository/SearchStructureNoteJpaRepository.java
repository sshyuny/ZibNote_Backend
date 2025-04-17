package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureNoteEntity;

public interface SearchStructureNoteJpaRepository extends JpaRepository<SearchStructureNoteEntity, Long> {
    
    List<SearchStructureNoteEntity> findAllBySearchStructureEntity(SearchStructureEntity searchStructureEntity);

    @Modifying
    @Query("UPDATE SearchStructureNoteEntity s SET s.isDeleted = 1, s.updatedAt = :updatedAt WHERE s.searchStructureNoteId = :searchStructureNoteId")
    void softDeleteBySearchStructureNoteId(@Param("searchStructureNoteId") Long searchStructureNoteId, @Param("updatedAt") LocalDateTime updatedAt);

}
