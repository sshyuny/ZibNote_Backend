package com.sshyu.zibnote.adapter.out.persistence.structure.mapper;

import com.sshyu.zibnote.adapter.out.persistence.structure.jpa.entity.StructureEntity;
import com.sshyu.zibnote.domain.structure.model.Structure;

public class StructureMapper {
    
    public static Structure toDomain(StructureEntity entity) {
        return Structure.builder()
                    .structureId(entity.getStructureId())
                    .name(entity.getName())
                    .address(entity.getAddress())
                    .latitude(entity.getLatitude())
                    .longitude(entity.getLongitude())
                    .builtYear(entity.getBuiltYear())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }

    public static StructureEntity toEntity(Structure domain) {
        return StructureEntity.builder()
                    .structureId(domain.getStructureId())
                    .name(domain.getName())
                    .address(domain.getAddress())
                    .latitude(domain.getLatitude())
                    .longitude(domain.getLongitude())
                    .builtYear(domain.getBuiltYear())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }

}
