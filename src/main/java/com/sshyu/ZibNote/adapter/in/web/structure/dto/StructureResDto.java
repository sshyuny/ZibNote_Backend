package com.sshyu.zibnote.adapter.in.web.structure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StructureResDto {
    
    private Long structureId;
    private String name;
    private String numberAddress;
    private String roadAddress;

}
