package com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;

public interface SearchStructureJpaRepository extends JpaRepository<SearchStructureEntity, Long> {
    
    List<SearchStructureEntity> findAllBySearchEntity(SearchEntity searchEntity);

}
