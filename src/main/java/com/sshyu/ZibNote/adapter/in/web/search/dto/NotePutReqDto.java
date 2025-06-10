package com.sshyu.zibnote.adapter.in.web.search.dto;

import java.util.UUID;

import com.sshyu.zibnote.domain.search.model.EvalType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NotePutReqDto {
    
    @Schema(description = "임장 건물 기록 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull
    private UUID searchStructureNoteId;

    @Schema(description = "임장 건물 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull
    private UUID searchStructureId;

    @Schema(description = "기록 기준 ID", example = "1234")
    @NotNull
    private Long noteFieldId;

    @Schema(description = "평가 타입", example = "STAR")
    private EvalType evalType;

    @Schema(description = "평가 값", example = "5")
    private String evalValue;

    @Schema(description = "노트", example = "Great")
    private String note;

}
