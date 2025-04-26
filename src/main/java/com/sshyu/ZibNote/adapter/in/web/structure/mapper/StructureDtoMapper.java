package com.sshyu.zibnote.adapter.in.web.structure.mapper;

import com.sshyu.zibnote.adapter.in.web.structure.dto.StructureResDto;
import com.sshyu.zibnote.domain.structure.model.Structure;

public class StructureDtoMapper {
    
    public static StructureResDto toResDto(Structure domain) {
        return StructureResDto.builder()
                    .structureId(domain.getStructureId())
                    .name(domain.getName())
                    .numberAddress(domain.getNumberAddress())
                    .roadAddress(domain.getRoadAddress())
                    .build();
    }
    
}
