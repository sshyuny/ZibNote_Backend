package com.sshyu.zibnote.application.service.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.in.StructureUseCase;
import com.sshyu.zibnote.domain.structure.port.out.StructureRepository;

@Service
public class StructureService implements StructureUseCase {

    @Autowired
    StructureRepository structureRepository;

    @Override
    public List<Structure> listStructuresByAddress(String address) {

        List<Structure> domainsByNumberAddress = structureRepository.findTop10ByNumberAddressContaining(address);

        if (domainsByNumberAddress.size() > 10) { return domainsByNumberAddress; }

        List<Structure> domainsByRoadAddress = structureRepository.findTop10ByRoadAddressContaining(address);

        Map<Long, Structure> domains = new HashMap<>();
        Stream.concat(domainsByNumberAddress.stream(), domainsByRoadAddress.stream())
                .forEach(domain -> domains.putIfAbsent(domain.getStructureId(), domain));

        return new ArrayList<>(domains.values());
    }

    @Override
    public List<Structure> listStructuresByName(String name) {
        return structureRepository.findTop10ByNameContaining(name);
    }
    
    
}
