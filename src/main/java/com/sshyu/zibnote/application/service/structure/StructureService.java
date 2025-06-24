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

    /**
     * ID로 Structure을 조회한다.
     * 
     * @param structureId 조회 대상 ID
     */
    @Override
    public Structure getStructure(final Long structureId) {

        return structureRepository.findByStructureId(structureId);
    }

    /**
     * 주소로 Structure을 검색한다.
     * 
     * <ol>
     *   <li>지번 주소로 검색하여 검색 결과가 8건 이상일 경우 바로 반환</li>
     *   <li>도로명 주소로 검색</li>
     *   <li>지번 주소와 도로명 주소 검색 결과를 합치되 중복 항목 제거한 뒤 반환</li>
     * </ol>
     * 
     * @param address 건물 주소
     * @return 검색된 Sturcture 목록
     */
    @Override
    public List<Structure> listStructuresByAddress(final String address) {

        final List<Structure> domainsByNumberAddress = structureRepository.findTop10ByNumberAddressContaining(address);
        if (domainsByNumberAddress.size() >= 8) { return domainsByNumberAddress; }

        final List<Structure> domainsByRoadAddress = structureRepository.findTop10ByRoadAddressContaining(address);

        final Map<Long, Structure> domains = new HashMap<>();
        Stream.concat(domainsByNumberAddress.stream(), domainsByRoadAddress.stream())
                .forEach(domain -> domains.putIfAbsent(domain.getStructureId(), domain));

        return new ArrayList<>(domains.values());
    }

    /**
     * 건물 이름으로 Structure을 검색한다.
     * 
     * @param name 건물 이름
     * @return 검색된 Structure 목록
     */
    @Override
    public List<Structure> listStructuresByName(final String name) {

        return structureRepository.findTop10ByNameContaining(name);
    }
    
    
}
