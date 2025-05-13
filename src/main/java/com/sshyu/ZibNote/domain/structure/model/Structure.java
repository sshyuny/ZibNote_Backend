package com.sshyu.zibnote.domain.structure.model;

import java.math.BigDecimal;

import com.sshyu.zibnote.domain.common.BaseFields;

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

    public static Structure onlyId(final Long structureId) {
        return Structure.builder()
                    .structureId(structureId)
                    .build();
    }

}
