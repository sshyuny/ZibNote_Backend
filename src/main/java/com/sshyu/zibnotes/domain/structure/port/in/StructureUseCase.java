package com.sshyu.zibnotes.domain.structure.port.in;

import java.util.List;

import com.sshyu.zibnotes.domain.structure.model.Structure;

public interface StructureUseCase {

    Structure getStructure(Long structureId);
    
    List<Structure> listStructuresByName(String name);

    /*
     * 지번주소, 도로명주소 통합 주소 검색
     */
    List<Structure> listStructuresByAddress(String address);

}
