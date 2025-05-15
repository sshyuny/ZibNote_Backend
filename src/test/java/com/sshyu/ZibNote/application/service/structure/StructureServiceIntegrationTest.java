package com.sshyu.zibnote.application.service.structure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.structure.model.Structure;
import com.sshyu.zibnote.fixture.StructureFixture;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class StructureServiceIntegrationTest {
    
    @Autowired
    StructureService sut;
    @Autowired
    EntityManager em;
    @Autowired
    StructurePersistenceAdapter structurePersistenceAdapter;

    Long structureIdOfSolApt;
    Long structureIdOfBaekduApt;

    @BeforeEach
    void setUp() {

        Structure structureSolApt = StructureFixture.ofStructureAptSollWithoutId();
        Structure structureBaekduApt = StructureFixture.ofStructureBaekduAptWithoutId();

        structureIdOfSolApt = structurePersistenceAdapter.save(structureSolApt);
        structureIdOfBaekduApt = structurePersistenceAdapter.save(structureBaekduApt);
    }

    @Test
    void listStructuresByAddress_서로_다른_두_데이터_조회() {

        List<Structure> list = sut.listStructuresByAddress("119");

        assertThat(list).hasSize(2);
    }

    @Test
    void listStructuresByAddress_중복데이터_필터링_확인() {

        List<Structure> list = sut.listStructuresByAddress("경기 군포시");

        assertThat(list).hasSize(2);
    }

    @Test
    void listStructuresByName_정상_조회() {

        List<Structure> list = sut.listStructuresByName("솔거");

        assertThat(list).hasSize(1);
    }

    @Test
    void listStructuresByName_빈_리스트_조회() {

        List<Structure> list = sut.listStructuresByName("깨비깨비 아파트");

        assertThat(list).hasSize(0);
    }

}
