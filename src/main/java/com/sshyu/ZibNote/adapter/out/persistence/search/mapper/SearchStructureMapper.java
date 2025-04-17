package com.sshyu.zibnote.adapter.out.persistence.search.mapper;

import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureEntity;
import com.sshyu.zibnote.adapter.out.persistence.structure.mapper.StructureMapper;
import com.sshyu.zibnote.domain.search.model.SearchStructure;

public class SearchStructureMapper {
    
    public static SearchStructure toDomain(SearchStructureEntity entity) {
        return SearchStructure.builder()
                    .searchStructureId(entity.getSearchStructureId())
                    .search(SearchMapper.toDomain(entity.getSearchEntity()))
                    .structure(StructureMapper.toDomain(entity.getStructureEntity()))
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }

    public static SearchStructureEntity toEntity(SearchStructure domain) {
        return SearchStructureEntity.builder()
                    .searchStructureId(domain.getSearchStructureId())
                    .searchEntity(SearchMapper.toEntity(domain.getSearch()))
                    .structureEntity(StructureMapper.toEntity(domain.getStructure()))
                    .description(domain.getDescription())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }
}
