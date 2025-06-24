package com.sshyu.zibnotes.domain.structure.port.out;

import java.util.List;

import com.sshyu.zibnotes.domain.structure.model.Structure;

public interface StructureRepository {

    Long save(Structure structure);

    Structure findByStructureId(final Long structureId);
    
    /**
     * 도로명주소를 이용한 검색
     */
    List<Structure> findTop10ByRoadAddressContaining(String keyword);

    /**
     * 지번주소를 이용한 검색
     */
    List<Structure> findTop10ByNumberAddressContaining(String keyword);

    /*
     * 건물 이름(아파트 이름)을 이용한 검색
     */
    List<Structure> findTop10ByNameContaining(String keyword);

}
