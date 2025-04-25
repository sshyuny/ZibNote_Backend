package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureNoteEntity;

public interface SearchStructureNoteJpaRepository extends JpaRepository<SearchStructureNoteEntity, Long> {
    
    List<SearchStructureNoteEntity> findAllBySearchStructureEntity(SearchStructureEntity searchStructureEntity);

}
