package com.sshyu.zibnote.adapter.in.web.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchStructureResDto {
    
    private String searchStructureId;

    private Long searchId;

    private String searchTitle;

    private Long structureId;

    private String structureName;

    private String description;
    
}
