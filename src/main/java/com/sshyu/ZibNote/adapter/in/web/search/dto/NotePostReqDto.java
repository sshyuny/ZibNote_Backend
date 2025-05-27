package com.sshyu.zibnote.adapter.in.web.search.dto;

import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.EvalType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NotePostReqDto {
    
    @NotNull
    private UUID searchStructureId;

    @NotNull
    private Long noteFieldId;

    private EvalType evalType;

    private String evalValue;

    private String note;

}
