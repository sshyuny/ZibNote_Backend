package com.sshyu.zibnote.adapter.in.web.search.dto;

import com.sshyu.zibnote.domain.search.model.EvalType;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class NotePostReqDto {
    
    @NotBlank
    private Long searchStructureId;

    @NotBlank
    private Long noteFieldId;

    private EvalType evalType;

    private String evalValue;

    private String note;

}
