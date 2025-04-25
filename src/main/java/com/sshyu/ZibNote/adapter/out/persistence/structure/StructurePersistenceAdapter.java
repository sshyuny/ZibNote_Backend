package com.sshyu.zibnote.adapter.out.persistence.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity.StructureEntity;
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
    public Long save(final Structure structure) {
        StructureEntity structureEntity = StructureMapper.toEntity(structure);
        structureJpaRepository.save(structureEntity);      
        return structureEntity.getStructureId();
    }

    @Override
    public Structure findByStructureId(final Long structureId) {
        StructureEntity structureEntity = structureJpaRepository.findById(structureId)
            .orElseThrow();
        return StructureMapper.toDomain(structureEntity);
    }
    
    @Override
    public List<Structure> findByAddressContaining(final String keyword) {

        List<Structure> domainsByNumberAddress = structureJpaRepository.findByNumberAddressContaining(keyword).stream()
            .map(entity -> StructureMapper.toDomain(entity))
            .collect(Collectors.toList());

        if (domainsByNumberAddress.size() > 10) { return domainsByNumberAddress; }

        List<Structure> domainsByRoadAddress = structureJpaRepository.findByRoadAddressContaining(keyword).stream()
            .map(entity -> StructureMapper.toDomain(entity))
            .collect(Collectors.toList());

        Map<Long, Structure> domains = new HashMap<>();
        Stream.concat(domainsByNumberAddress.stream(), domainsByRoadAddress.stream())
                .forEach(domain -> domains.putIfAbsent(domain.getStructureId(), domain));

        return new ArrayList<>(domains.values());
    }

    @Override
    public List<Structure> findByNameContaining(final String keyword) {

        return structureJpaRepository.findByNameContaining(keyword).stream()
            .map(entity -> StructureMapper.toDomain(entity))
            .collect(Collectors.toList());
    }

}
