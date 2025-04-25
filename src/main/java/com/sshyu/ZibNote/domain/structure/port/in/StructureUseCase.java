package com.sshyu.zibnote.domain.structure.port.in;

import java.util.List;

import com.sshyu.zibnote.domain.structure.model.Structure;

public interface StructureUseCase {
    
    List<Structure> listStructuresByName(String name);

    /*
     * 지번주소, 도로명주소 통합 주소 검색
     */
    List<Structure> listStructuresByAddress(String address);

}
