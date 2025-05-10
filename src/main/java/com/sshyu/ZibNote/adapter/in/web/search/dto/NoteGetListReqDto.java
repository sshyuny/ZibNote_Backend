package com.sshyu.zibnote.adapter.in.web.search.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoteGetListReqDto {
    
    @NotBlank
    private Long searchStructureId;

}
