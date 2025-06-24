package com.sshyu.zibnotes.domain.structure.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sshyu.zibnotes.domain.common.BaseFields;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter @SuperBuilder
public class Structure extends BaseFields {
    
    private final Long structureId;

    private final String name;

    private final String numberAddress;

    private final String roadAddress;

    private final BigDecimal latitude;

    private final BigDecimal longitude;

    private final Integer builtYear;

    public static Structure ofFull(final Long structureId, final String name, final String numberAddress, final String roadAddress, 
            final BigDecimal latitude, final BigDecimal longitude, final Integer builtYear, 
            final LocalDateTime createdAt, final LocalDateTime updatedAt, final Integer isDeleted) {
        return Structure.builder()
            .structureId(structureId)
            .name(name)
            .numberAddress(numberAddress)
            .roadAddress(numberAddress)
            .latitude(latitude)
            .longitude(longitude)
            .builtYear(builtYear)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .isDeleted(isDeleted)
            .build();
    }

    public static Structure ofBasic(final Long structureId, final String name, final String numberAddress, final String roadAddress, 
            final BigDecimal latitude, final BigDecimal longitude, final Integer builtYear) {
        return Structure.builder()
            .structureId(structureId)
            .name(name)
            .numberAddress(numberAddress)
            .roadAddress(numberAddress)
            .latitude(latitude)
            .longitude(longitude)
            .builtYear(builtYear)
            .build();
    }

    public static Structure onlyId(final Long structureId) {
        return Structure.builder()
                    .structureId(structureId)
                    .build();
    }

}
