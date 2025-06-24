package com.sshyu.zibnote.adapter.in.web.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NoteResDto {
    
    @Schema(description = "임장 건물 기록 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String searchStructureNoteId;

    @Schema(description = "임장 건물 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String searchStructureId;

    @Schema(description = "기록 기준 ID", example = "1234")
    private Long noteFieldId;

    @Schema(description = "평가 타입", example = "STAR")
    private String evalType;

    @Schema(description = "평가 값", example = "5")
    private String evalValue;

    @Schema(description = "노트", example = "Great")
    private String  note;

}
