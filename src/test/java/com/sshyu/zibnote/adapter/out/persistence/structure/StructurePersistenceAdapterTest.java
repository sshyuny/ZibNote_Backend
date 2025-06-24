package com.sshyu.zibnote.adapter.out.persistence.structure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.adapter.out.persistence.structure.StructurePersistenceAdapter;
import com.sshyu.zibnote.domain.structure.exception.StructureNotFoundException;
import com.sshyu.zibnote.domain.structure.model.Structure;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import(StructurePersistenceAdapter.class)
@ActiveProfiles("test")
public class StructurePersistenceAdapterTest {

    @Autowired
    EntityManager em;
    @Autowired
    StructurePersistenceAdapter structurePersistenceAdapter;

    // data value
    final static String STRUCTURE_NAME_1 = "목화아파트";
    final static String NUMBER_ADDRESS_1 = "경기 군포시 금정동 850";
    final static String ROAD_ADDRESS_1 = "경기 군포시 번영로550번길 5";
    final static BigDecimal LATITUDE_1 = BigDecimal.valueOf(1.1);
    final static BigDecimal LONGITUDE = BigDecimal.valueOf(2.1);
    final static Integer BUILT_YEAR_1 = 1992;
    final static String STRUCTURE_NAME_2 = "대림솔거아파트";
    final static String NUMBER_ADDRESS_2 = "경기 군포시 산본동 1146";
    final static String ROAD_ADDRESS_2 = "경기 군포시 광정로 119";
    // assert valus
    final static LocalDateTime TIME = LocalDateTime.now();
    final static LocalDateTime PLUS_TIME = TIME.plusMinutes(1);
    final static LocalDateTime MINUS_TIME = TIME.minusMinutes(1);
    final static Long NOT_EXIST_ID = 2345554L;

    Long structureId1;
    Long structureId2;

    @BeforeEach
    void 기초데이터생성() {

        structureId1 = structurePersistenceAdapter.save(
            Structure.builder()
                .name(STRUCTURE_NAME_1)
                .numberAddress(NUMBER_ADDRESS_1)
                .roadAddress(ROAD_ADDRESS_1)
                .latitude(LATITUDE_1)
                .longitude(LONGITUDE)
                .builtYear(BUILT_YEAR_1)
                .build()
        );

        structureId2 = structurePersistenceAdapter.save(
            Structure.builder()
                .name(STRUCTURE_NAME_2)
                .numberAddress(NUMBER_ADDRESS_2)
                .roadAddress(ROAD_ADDRESS_2)
                .latitude(null)
                .longitude(null)
                .builtYear(1993)
                .build()
        );
    }

    @Test
    void save_새로운데이터_저장() {
        em.flush();
        em.clear();

        Structure structure1 = structurePersistenceAdapter.findByStructureId(structureId1);

        assertThat(structure1.getName()).isEqualTo(STRUCTURE_NAME_1);
        assertThat(structure1.getNumberAddress()).isEqualTo(NUMBER_ADDRESS_1);
        assertThat(structure1.getRoadAddress()).isEqualTo(ROAD_ADDRESS_1);
        assertThat(structure1.getLatitude()).usingComparator(BigDecimal::compareTo).isEqualTo(LATITUDE_1);
        assertThat(structure1.getLongitude()).usingComparator(BigDecimal::compareTo).isEqualTo(LONGITUDE);
        assertThat(structure1.getBuiltYear()).isEqualTo(BUILT_YEAR_1);
        assertThat(structure1.getCreatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(structure1.getUpdatedAt()).isBefore(PLUS_TIME).isAfter(MINUS_TIME);
        assertThat(structure1.getIsDeleted()).isEqualTo(0);
    }

    @Test
    void findByNameContaining_이름검색_테스트() {
        List<Structure> structures1 = structurePersistenceAdapter.findTop10ByNameContaining("아파트");
        List<Structure> structures2 = structurePersistenceAdapter.findTop10ByNameContaining("대림솔거");

        assertThat(structures1.size()).isEqualTo(2);
        assertThat(structures2.get(0).getName()).isEqualTo(STRUCTURE_NAME_2);
    }

    @Test
    void findByStructureId_존재하지_않는_데이터_조회() {

        assertThrows(StructureNotFoundException.class, () -> 
            structurePersistenceAdapter.findByStructureId(NOT_EXIST_ID)
        );
    }
    
}
