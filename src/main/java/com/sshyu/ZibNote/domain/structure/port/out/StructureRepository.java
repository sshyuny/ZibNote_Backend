package com.sshyu.zibnote.domain.structure.port.out;

import com.sshyu.zibnote.domain.structure.model.Structure;

public interface StructureRepository {

    void save(Structure structure);
    
    Structure findByAddress(String address);

}
