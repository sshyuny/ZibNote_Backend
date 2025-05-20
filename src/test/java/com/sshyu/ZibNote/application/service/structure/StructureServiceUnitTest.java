package com.sshyu.zibnote.application.service.structure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.domain.structure.port.out.StructureRepository;
import com.sshyu.zibnote.fixture.StructureFixture;

@ExtendWith(MockitoExtension.class)
public class StructureServiceUnitTest {
    
    @InjectMocks
    StructureService sut;
    @Mock
    StructureRepository structureRepository;

    Structure structure1 = StructureFixture.validStructure1();
    Structure structure2 = StructureFixture.validStructure2();

    List<Structure> structuresOnly1 = List.of(structure1);
    List<Structure> structuresOnly2 = List.of(structure2);
    List<Structure> structuresWhole1 = List.of(structure1, structure2);
    List<Structure> structuresWhole2= List.of(structure1, structure2);


    @Test
    void listStructuresByAddress_서로_다른_두_데이터_조회() {

        given(structureRepository.findTop10ByNumberAddressContaining("6"))
            .willReturn(structuresOnly1);
        given(structureRepository.findTop10ByRoadAddressContaining("6"))
            .willReturn(structuresOnly2);
        
        List<Structure> structures = sut.listStructuresByAddress("6");

        assertThat(structures.size()).isEqualTo(2);
    }

    @Test
    void listStructuresByAddress_중복데이터_필터링_확인() {

        given(structureRepository.findTop10ByNumberAddressContaining("경기 군포시"))
            .willReturn(structuresWhole1);
        given(structureRepository.findTop10ByRoadAddressContaining("경기 군포시"))
            .willReturn(structuresWhole2);
        
        List<Structure> structures = sut.listStructuresByAddress("경기 군포시");

        assertThat(structures.size()).isEqualTo(2);
    }

    @Test
    void listStructuresByName_정상_조회() {
        //given
        String keyword = "아파트";
        given(structureRepository.findTop10ByNameContaining(keyword))
            .willReturn(structuresWhole1);

        //when/then
        assertThat(sut.listStructuresByName(keyword))
            .hasSize(2)
            .contains(structure1, structure2);
    }

    @Test
    void listStructuresByName_빈_리스트_조회() {
        //given
        String keyword = "깨비깨비 아파트";
        given(structureRepository.findTop10ByNameContaining(keyword))
            .willReturn(List.of());

        //when/then
        assertThat(sut.listStructuresByName(keyword))
            .hasSize(0);
    }

}
