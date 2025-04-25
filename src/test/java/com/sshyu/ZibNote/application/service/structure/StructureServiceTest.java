package com.sshyu.zibnote.application.service.structure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.out.StructureRepository;

@ExtendWith(MockitoExtension.class)
public class StructureServiceTest {
    
    @Mock
    StructureRepository structureRepository;
    @InjectMocks
    StructureService structureService;

    // data value
    final static Long STRUCTURE_ID_1 = 22L;
    final static String STRUCTURE_NAME_1 = "극동백두아파트";
    final static String NUMBER_ADDRESS_1 = "경기 군포시 산본동 1119-3";
    final static String ROAD_ADDRESS_1 = "경기 군포시 고산로539번길 6";
    final static Long STRUCTURE_ID_2 = 333L;
    final static String STRUCTURE_NAME_2 = "대림솔거아파트";
    final static String NUMBER_ADDRESS_2 = "경기 군포시 산본동 1146";
    final static String ROAD_ADDRESS_2 = "경기 군포시 광정로 119";

    List<Structure> structuresByNumberAdrs = new ArrayList<>();
    List<Structure> structuresByRoadAdrs = new ArrayList<>();
    List<Structure> structuresWithSameDataByNumberAdrs = new ArrayList<>();
    List<Structure> structuresWithSameDataByRoadAdrs= new ArrayList<>();

    @BeforeEach
    void beforeEach() {

        Structure structure1 = Structure.builder()
            .structureId(STRUCTURE_ID_1)
            .name(STRUCTURE_NAME_1)
            .numberAddress(NUMBER_ADDRESS_1)
            .roadAddress(ROAD_ADDRESS_1)
            .build();

        Structure structure2 = Structure.builder()
            .structureId(STRUCTURE_ID_2)
            .name(STRUCTURE_NAME_2)
            .numberAddress(NUMBER_ADDRESS_2)
            .roadAddress(ROAD_ADDRESS_2)
            .build();

        // 정상 조회
        structuresByNumberAdrs.add(structure1);
        structuresByRoadAdrs.add(structure2);
        // 중복데이터 포함되는 조회
        structuresWithSameDataByNumberAdrs.add(structure1);
        structuresWithSameDataByNumberAdrs.add(structure2);
        structuresWithSameDataByRoadAdrs.add(structure1);
        structuresWithSameDataByRoadAdrs.add(structure2);
    }

    @Test
    void listStructuresByAddress_정상_조회() {

        given(structureRepository.findByNumberAddressContaining("경기 군포시 산본동"))
            .willReturn(structuresByNumberAdrs);
        given(structureRepository.findByRoadAddressContaining("경기 군포시 산본동"))
            .willReturn(structuresByRoadAdrs);
        
        List<Structure> structures = structureService.listStructuresByAddress("경기 군포시 산본동");

        assertThat(structures.size()).isEqualTo(2);
    }

    @Test
    void listStructuresByAddress_중복데이터_필터링_확인() {

        given(structureRepository.findByNumberAddressContaining("경기 군포시"))
            .willReturn(structuresWithSameDataByNumberAdrs);
        given(structureRepository.findByRoadAddressContaining("경기 군포시"))
            .willReturn(structuresWithSameDataByRoadAdrs);
        
        List<Structure> structures = structureService.listStructuresByAddress("경기 군포시");

        assertThat(structures.size()).isEqualTo(2);
    }
}
