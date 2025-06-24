package com.sshyu.zibnotes.adapter.out.persistence.structure.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnotes.adapter.out.persistence.structure.jpa.entity.StructureEntity;

public interface StructureJpaRepository extends JpaRepository<StructureEntity, Long> {
    
    List<StructureEntity> findTop10ByRoadAddressContaining(String keyword);

    List<StructureEntity> findTop10ByNumberAddressContaining(String keyword);

    List<StructureEntity> findTop10ByNameContaining(String keyword);
    
}
