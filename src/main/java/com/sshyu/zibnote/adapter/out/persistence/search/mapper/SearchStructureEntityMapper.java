package com.sshyu.zibnote.adapter.out.persistence.search.mapper;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.structure.mapper.StructureEntityMapper;
import com.sshyu.zibnote.domain.search.model.SearchStructure;

public class SearchStructureEntityMapper {
    
    public static SearchStructure toDomain(final SearchStructureEntity entity) {
        return SearchStructure.builder()
                    .searchStructureId(entity.getSearchStructureId())
                    .search(SearchEntityMapper.toDomain(entity.getSearchEntity()))
                    .structure(StructureEntityMapper.toDomain(entity.getStructureEntity()))
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }

    public static SearchStructureEntity toEntity(final SearchStructure domain) {
        return SearchStructureEntity.builder()
                    .searchStructureId(domain.getSearchStructureId())
                    .searchEntity(SearchEntityMapper.toEntity(domain.getSearch()))
                    .structureEntity(StructureEntityMapper.toEntity(domain.getStructure()))
                    .description(domain.getDescription())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }
}
