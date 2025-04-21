package com.sshyu.zibnote.adapter.out.persistence.structure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sshyu.zibnote.domain.structure.model.Structure;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Import(StructurePersistenceAdapter.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StructurePersistenceAdapterTest {

    @Autowired
    EntityManager em;
    @Autowired
    StructurePersistenceAdapter structurePersistenceAdapter;

    final LocalDateTime now = LocalDateTime.now();
    final String structureName1 = "목화아파트";
    final String numberAddress1 = "경기 군포시 금정동 850";
    final String roadAddress1 = "경기 군포시 번영로550번길 5";
    final String structureName2 = "대림솔거아파트";
    final String numberAddress2 = "경기 군포시 산본동 1146";
    final String roadAddress2 = "경기 군포시 광정로 119";

    @BeforeEach
    void 기초데이터생성() {

        structurePersistenceAdapter.save(
            Structure.builder()
                .name(structureName1)
                .numberAddress(numberAddress1)
                .roadAddress(roadAddress1)
                .latitude(null)
                .longitude(null)
                .builtYear(1992)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );

        structurePersistenceAdapter.save(
            Structure.builder()
                .name(structureName2)
                .numberAddress(numberAddress2)
                .roadAddress(roadAddress2)
                .latitude(null)
                .longitude(null)
                .builtYear(1993)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(0)
                .build()
        );
    }

    @Test
    void 주소검색_중복데이터필터링_테스트() {

        List<Structure> structures = structurePersistenceAdapter.findByAddressContaining("경기 군포시");

        assertThat(structures.size()).isEqualTo(2);
    }

    @Test
    void 주소검색_테스트() {

        List<Structure> structures = structurePersistenceAdapter.findByAddressContaining("금정동 850");
        
        assertThat(structures.size()).isEqualTo(1);
        assertThat(structureName1).isEqualTo(structures.get(0).getName());
    }

    @Test
    void 이름검색_테스트() {
        List<Structure> structures1 = structurePersistenceAdapter.findByNameContaining("아파트");
        List<Structure> structures2 = structurePersistenceAdapter.findByNameContaining("대림솔거");

        assertThat(structures1.size()).isEqualTo(2);
        assertThat(structures2.get(0).getName()).isEqualTo(structureName2);
    }
    
}
