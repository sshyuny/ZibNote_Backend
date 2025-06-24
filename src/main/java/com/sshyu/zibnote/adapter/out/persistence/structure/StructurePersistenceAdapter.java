package com.sshyu.zibnote.adapter.out.persistence.structure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity.StructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.repository.StructureJpaRepository;
import com.sshyu.zibnote.adapter.out.persistence.structure.mapper.StructureEntityMapper;
import com.sshyu.zibnote.domain.structure.exception.StructureNotFoundException;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.out.StructureRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Repository
public class StructurePersistenceAdapter implements StructureRepository {

    private final StructureJpaRepository structureJpaRepository;

    @Override
    public Long save(final Structure structure) {

        final StructureEntity structureEntity = StructureEntityMapper.toEntity(structure);
        structureJpaRepository.save(structureEntity);

        return structureEntity.getStructureId();
    }

    @Override
    public Structure findByStructureId(final Long structureId) {

        final StructureEntity structureEntity = structureJpaRepository.findById(structureId)
            .orElseThrow(() -> new StructureNotFoundException());
            
        return StructureEntityMapper.toDomain(structureEntity);
    }

    @Override
    public List<Structure> findTop10ByNumberAddressContaining(final String keyword) {

        return structureJpaRepository.findTop10ByNumberAddressContaining(keyword).stream()
            .map(entity -> StructureEntityMapper.toDomain(entity))
            .collect(Collectors.toList());
    }

    @Override
    public List<Structure> findTop10ByRoadAddressContaining(final String keyword) {

        return structureJpaRepository.findTop10ByRoadAddressContaining(keyword).stream()
            .map(entity -> StructureEntityMapper.toDomain(entity))
            .collect(Collectors.toList());
    }

    @Override
    public List<Structure> findTop10ByNameContaining(final String keyword) {

        return structureJpaRepository.findTop10ByNameContaining(keyword).stream()
            .map(entity -> StructureEntityMapper.toDomain(entity))
            .collect(Collectors.toList());
    }

}
