package com.sshyu.zibnote.adapter.out.persistence.search;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.repository.SearchStructureJpaRepository;
import com.sshyu.zibnote.adapter.out.persistence.search.mapper.SearchMapper;
import com.sshyu.zibnote.adapter.out.persistence.search.mapper.SearchStructureMapper;
import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity.StructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.structure.mapper.StructureMapper;
import com.sshyu.zibnote.domain.search.exception.SearchStructureNotFoundException;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.port.out.SearchStructureRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SearchStructurePersistenceAdapter implements SearchStructureRepository {
    
    private final SearchStructureJpaRepository searchStructureJpaRepository;

    @Override
    public void save(final SearchStructure searchStructure) {

        searchStructure.validate();

        final SearchEntity searchRef = SearchEntity.builder()
            .searchId(searchStructure.getSearch().getSearchId())
            .build();
        final StructureEntity structureRef = StructureEntity.builder()
            .structureId(searchStructure.getStructure().getStructureId())
            .build();

        searchStructureJpaRepository.save(
            SearchStructureEntity.builder()
                .searchEntity(searchRef)
                .structureEntity(structureRef)
                .description(searchStructure.getDescription())
                .createdAt(searchStructure.getCreatedAt())
                .updatedAt(searchStructure.getUpdatedAt())
                .isDeleted(searchStructure.getIsDeleted())
                .build()
        );
    }

    @Override
    public SearchStructure findBySearchStructureId(final Long searchStructureId) {

        final SearchStructureEntity searchStructureEntity = searchStructureJpaRepository.findById(searchStructureId)
            .orElseThrow(() -> new SearchStructureNotFoundException());

        return SearchStructure.builder()
                    .searchStructureId(searchStructureEntity.getSearchStructureId())
                    .search(SearchMapper.toDomain(searchStructureEntity.getSearchEntity()))
                    .structure(StructureMapper.toDomain(searchStructureEntity.getStructureEntity()))
                    .description(searchStructureEntity.getDescription())
                    .createdAt(searchStructureEntity.getCreatedAt())
                    .updatedAt(searchStructureEntity.getUpdatedAt())
                    .isDeleted(searchStructureEntity.getIsDeleted())
                    .build();
    }

    @Override
    public List<SearchStructure> findAllBySearchId(final Long searchId) {

        SearchEntity searchRef = SearchEntity.builder().searchId(searchId).build();

        return searchStructureJpaRepository.findAllBySearchEntity(searchRef).stream()
                    .map(result -> SearchStructureMapper.toDomain(result))
                    .collect(Collectors.toList());
    }

    @Override
    public void softDeleteBySearchStructureId(final Long searchStructureId, final LocalDateTime updatedAt) {
        searchStructureJpaRepository.softDeleteBySearchStructureId(searchStructureId, updatedAt);
    }

    
}
