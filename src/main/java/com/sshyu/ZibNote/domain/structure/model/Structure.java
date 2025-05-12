package com.sshyu.zibnote.domain.structure.model;

import java.math.BigDecimal;

import com.sshyu.zibnote.domain.common.BaseFields;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter @SuperBuilder
public class Structure extends BaseFields {
    
    private Long structureId;
    private String name;
    private String numberAddress;
    private String roadAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer builtYear;

    public static Structure onlyId(Long structureId) {
        return Structure.builder()
                    .structureId(structureId)
                    .build();
    }

}
