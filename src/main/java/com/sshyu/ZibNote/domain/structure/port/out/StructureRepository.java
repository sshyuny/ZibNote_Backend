package com.sshyu.zibnote.domain.structure.port.out;

import java.util.List;

import com.sshyu.zibnote.domain.structure.model.Structure;

public interface StructureRepository {

    Long save(Structure structure);

    Structure findByStructureId(final Long structureId);
    
    List<Structure> findByRoadAddressContaining(String keyword);

    List<Structure> findByNumberAddressContaining(String keyword);

    /*
     * 건물 이름(아파트 이름)을 이용한 검색
     */
    List<Structure> findByNameContaining(String keyword);

}
