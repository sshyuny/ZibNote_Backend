package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.structure.model.Structure;

public class StructureFixture {

    public static final Long STRUCTURE_1_ID = 3001L;
    public static final Long STRUCTURE_2_ID = 3002L;
    public static final String STRUCTURE_1_NAME = "대림솔거아파트";
    public static final String STRUCTURE_2_NAME = "극동백두아파트";
    public static final String STRUCTURE_1_NUMBER_ADDRESS = "경기 군포시 산본동 1146";
    public static final String STRUCTURE_2_NUMBER_ADDRESS = "경기 군포시 산본동 1119-3";
    public final static String STRUCTURE_1_ROAD_ADDRESS = "경기 군포시 광정로 119";
    public final static String STRUCTURE_2_ROAD_ADDRESS = "경기 군포시 고산로539번길 6";

    public static Structure validStructure1() {
        return Structure.builder()
            .structureId(STRUCTURE_1_ID)
            .name(STRUCTURE_1_NAME)
            .numberAddress(STRUCTURE_1_NUMBER_ADDRESS)
            .roadAddress(STRUCTURE_1_ROAD_ADDRESS)
            .builtYear(1992)
            .build();
    }
    public static Structure validStructure2() {
        return Structure.builder()
            .structureId(STRUCTURE_2_ID)
            .name(STRUCTURE_2_NAME)
            .numberAddress(STRUCTURE_2_NUMBER_ADDRESS)
            .roadAddress(STRUCTURE_2_ROAD_ADDRESS)
            .builtYear(1994)
            .build();
    }

    public static Structure validStructure1WithoutId() {
        return Structure.builder()
            .structureId(null)
            .name(STRUCTURE_1_NAME)
            .numberAddress(STRUCTURE_1_NUMBER_ADDRESS)
            .roadAddress(STRUCTURE_1_ROAD_ADDRESS)
            .builtYear(1992)
            .build();
    }
    public static Structure validStructure2WithoutId() {
        return Structure.builder()
            .structureId(null)
            .name(STRUCTURE_2_NAME)
            .numberAddress(STRUCTURE_2_NUMBER_ADDRESS)
            .roadAddress(STRUCTURE_2_ROAD_ADDRESS)
            .builtYear(1994)
            .build();
    }

}
