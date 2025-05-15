package com.sshyu.zibnote.fixture;

import com.sshyu.zibnote.domain.structure.model.Structure;

public class StructureFixture {

    public static final Long STRUCTURE_ID_SOL = 3001L;
    public static final Long STRUCTURE_ID_BAEKDU = 3002L;

    public static final String NAME_APT_SOL = "대림솔거아파트";
    public static final String NAME_APT_BAEKDU = "극동백두아파트";
    public static final String NUMBER_ADDRESS_APT_SOL = "경기 군포시 산본동 1146";
    public static final String NUMBER_ADDRESS_APT_BAEKDU = "경기 군포시 산본동 1119-3";
    public final static String ROAD_ADDRESS_APT_SOL = "경기 군포시 광정로 119";
    public final static String ROAD_ADDRESS_APT_BAEKDU = "경기 군포시 고산로539번길 6";

    public static Structure ofStructureSolApt() {
        return Structure.builder()
            .structureId(STRUCTURE_ID_SOL)
            .name(NAME_APT_SOL)
            .numberAddress(NUMBER_ADDRESS_APT_SOL)
            .roadAddress(ROAD_ADDRESS_APT_SOL)
            .builtYear(1992)
            .build();
    }
    public static Structure ofStructureBaekduApt() {
        return Structure.builder()
            .structureId(STRUCTURE_ID_BAEKDU)
            .name(NAME_APT_BAEKDU)
            .numberAddress(NUMBER_ADDRESS_APT_BAEKDU)
            .roadAddress(ROAD_ADDRESS_APT_BAEKDU)
            .builtYear(1994)
            .build();
    }

    public static Structure ofStructureAptSollWithoutId() {
        return Structure.builder()
            .name(NAME_APT_SOL)
            .numberAddress(NUMBER_ADDRESS_APT_SOL)
            .roadAddress(ROAD_ADDRESS_APT_SOL)
            .builtYear(1992)
            .build();
    }
    public static Structure ofStructureBaekduAptWithoutId() {
        return Structure.builder()
            .name(NAME_APT_BAEKDU)
            .numberAddress(NUMBER_ADDRESS_APT_BAEKDU)
            .roadAddress(ROAD_ADDRESS_APT_BAEKDU)
            .builtYear(1994)
            .build();
    }

}
