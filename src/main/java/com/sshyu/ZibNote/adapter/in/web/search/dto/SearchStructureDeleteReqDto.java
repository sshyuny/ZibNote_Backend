package com.sshyu.zibnote.adapter.in.web.search.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class SearchStructureDeleteReqDto {
    
    @NotBlank
    private Long searchStructureId;
    
}
