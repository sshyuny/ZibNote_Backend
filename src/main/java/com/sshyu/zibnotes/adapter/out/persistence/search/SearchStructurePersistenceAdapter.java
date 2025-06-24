package com.sshyu.zibnotes.adapter.out.persistence.search;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.repository.SearchStructureJpaRepository;
import com.sshyu.zibnotes.adapter.out.persistence.search.mapper.SearchEntityMapper;
import com.sshyu.zibnotes.adapter.out.persistence.search.mapper.SearchStructureEntityMapper;
import com.sshyu.zibnotes.adapter.out.persistence.structure.jpa.entity.StructureEntity;
import com.sshyu.zibnotes.adapter.out.persistence.structure.mapper.StructureEntityMapper;
import com.sshyu.zibnotes.domain.search.exception.SearchStructureNotFoundException;
import com.sshyu.zibnotes.domain.search.model.SearchStructure;
import com.sshyu.zibnotes.domain.search.port.out.SearchStructureRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Repository
public class SearchStructurePersistenceAdapter implements SearchStructureRepository {
    
    private final SearchStructureJpaRepository searchStructureJpaRepository;

    @Override
    public UUID save(final SearchStructure searchStructure) {

        final SearchEntity searchRef = SearchEntity.ref(searchStructure.getSearch().getSearchId());
        final StructureEntity structureRef = StructureEntity.ref(searchStructure.getStructure().getStructureId());

        final SearchStructureEntity searchStructureEntity = SearchStructureEntity.builder()
            .searchEntity(searchRef)
            .structureEntity(structureRef)
            .description(searchStructure.getDescription())
            .build();
            
        searchStructureJpaRepository.save(searchStructureEntity);
        return searchStructureEntity.getSearchStructureId();
    }

    @Override
    public SearchStructure findBySearchStructureId(final UUID searchStructureId) {

        final SearchStructureEntity searchStructureEntity = searchStructureJpaRepository.findById(searchStructureId)
            .orElseThrow(() -> new SearchStructureNotFoundException());

        return SearchStructure.builder()
                    .searchStructureId(searchStructureEntity.getSearchStructureId())
                    .search(SearchEntityMapper.toDomain(searchStructureEntity.getSearchEntity()))
                    .structure(StructureEntityMapper.toDomain(searchStructureEntity.getStructureEntity()))
                    .description(searchStructureEntity.getDescription())
                    .createdAt(searchStructureEntity.getCreatedAt())
                    .updatedAt(searchStructureEntity.getUpdatedAt())
                    .isDeleted(searchStructureEntity.getIsDeleted())
                    .build();
    }

    @Override
    public List<SearchStructure> findAllBySearchId(final UUID searchId) {

        final SearchEntity searchRef = SearchEntity.ref(searchId);

        return searchStructureJpaRepository.findAllBySearchEntity(searchRef).stream()
                    .map(result -> SearchStructureEntityMapper.toDomain(result))
                    .collect(Collectors.toList());
    }

    @Override
    public void softDeleteBySearchStructureId(final UUID searchStructureId) {

        final SearchStructureEntity searchStructureEntity = searchStructureJpaRepository.findById(searchStructureId)
            .orElseThrow(() -> new SearchStructureNotFoundException());

        searchStructureEntity.softDelete();
        searchStructureJpaRepository.save(searchStructureEntity);
    }

    @Override
    public void updateBySearchStructure(SearchStructure searchStructure) {
        
        final SearchStructureEntity entity = searchStructureJpaRepository.findById(searchStructure.getSearchStructureId())
            .orElseThrow(() -> new SearchStructureNotFoundException());

        entity.update(searchStructure);
        searchStructureJpaRepository.save(entity);
    }

}
