package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureNoteEntity;

public interface SearchStructureNoteJpaRepository extends JpaRepository<SearchStructureNoteEntity, UUID> {
    
    List<SearchStructureNoteEntity> findAllBySearchStructureEntity(SearchStructureEntity searchStructureEntity);

}
