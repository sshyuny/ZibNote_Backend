package com.sshyu.zibnote.adapter.in.web.search.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class SearchStructureReqDto {
    
    @NotBlank
    private Long searchId;

    @NotBlank
    private Long structureId;

    @NotBlank
    private String description;

}
