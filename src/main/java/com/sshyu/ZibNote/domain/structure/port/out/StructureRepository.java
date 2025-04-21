package com.sshyu.zibnote.domain.structure.port.out;

import java.util.List;

import com.sshyu.zibnote.domain.structure.model.Structure;

public interface StructureRepository {

    void save(Structure structure);
    
    /*
     * 지번주소, 도로명주소 통합 주소 검색
     */
    List<Structure> findByAddressContaining(String keyword);

    /*
     * 건물 이름(아파트 이름)을 이용한 주소 검색
     */
    List<Structure> findByNameContaining(String keyword);

}
