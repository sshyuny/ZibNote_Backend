package com.sshyu.zibnote.adapter.out.persistence.structure.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity.StructureEntity;

public interface StructureJpaRepository extends JpaRepository<StructureEntity, Long> {
    
    List<StructureEntity> findByRoadAddressContaining(String keyword);

    List<StructureEntity> findByNumberAddressContaining(String keyword);

    List<StructureEntity> findByNameContaining(String keyword);
    
}
