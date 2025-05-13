package com.sshyu.zibnote.adapter.in.web.structure.mapper;

import com.sshyu.zibnote.adapter.in.web.structure.dto.StructureResDto;
import com.sshyu.zibnote.domain.structure.model.Structure;

public class StructureDtoMapper {
    
    public static StructureResDto toResDto(final Structure domain) {
        return new StructureResDto(
            domain.getStructureId(),
            domain.getName(),
            domain.getNumberAddress(),
            domain.getRoadAddress()
        );
    }
    
}
