package com.sshyu.zibnote.domain.structure.port.out;

import java.util.List;

import com.sshyu.zibnote.domain.structure.model.Structure;

public interface StructureRepository {

    void save(Structure structure);
    
    List<Structure> findByAddressContaining(String address);

}
