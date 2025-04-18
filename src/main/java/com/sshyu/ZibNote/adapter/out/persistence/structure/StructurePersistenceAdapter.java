package com.sshyu.zibnote.adapter.out.persistence.structure;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.repository.StructureJpaRepository;
import com.sshyu.zibnote.adapter.out.persistence.structure.mapper.StructureMapper;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.out.StructureRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Repository
public class StructurePersistenceAdapter implements StructureRepository {

    private final StructureJpaRepository structureJpaRepository;

    @Override
    public void save(final Structure structure) {
        structureJpaRepository.save(StructureMapper.toEntity(structure));      
    }
    
    @Override
    public Structure findByAddress(final String address) {
        
        return StructureMapper.toDomain(
            structureJpaRepository.findByAddress(address)
                .orElseThrow(() -> new RuntimeException())
        );
    }

}
