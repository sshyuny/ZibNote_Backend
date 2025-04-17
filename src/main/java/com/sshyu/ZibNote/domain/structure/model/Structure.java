package com.sshyu.zibnote.domain.structure.model;

import java.math.BigDecimal;

import com.sshyu.zibnote.domain.common.BaseFields;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter @SuperBuilder
public class Structure extends BaseFields {
    
    private Long structureId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer builtYear;

}
