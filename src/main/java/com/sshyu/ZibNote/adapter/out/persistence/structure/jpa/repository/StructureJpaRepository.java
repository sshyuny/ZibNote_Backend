package com.sshyu.zibnote.adapter.out.persistence.structure.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity.StructureEntity;

public interface StructureJpaRepository extends JpaRepository<StructureEntity, Long> {
    
    Optional<StructureEntity> findByAddress(String address);
    
}
