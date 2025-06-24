package com.sshyu.zibnotes.adapter.in.web.structure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class StructureResDto {
    
    private Long structureId;
    
    private String name;
    
    private String numberAddress;
    
    private String roadAddress;

}
