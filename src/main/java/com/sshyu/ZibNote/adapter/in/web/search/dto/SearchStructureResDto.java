package com.sshyu.zibnote.adapter.in.web.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class SearchStructureResDto {
    
    private Long searchStructureId;

    private Long searchId;

    private Long structureId;

    private String description;
    
}
