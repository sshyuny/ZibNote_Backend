package com.sshyu.zibnote.adapter.in.web.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchStructureResDto {
    
    @Schema(description = "임장 건물 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String searchStructureId;

    @Schema(description = "임장 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String searchId;

    @Schema(description = "임장 제목", example = "산본역 임장")
    private String searchTitle;

    @Schema(description = "건물 ID", example = "1234")
    private Long structureId;

    @Schema(description = "건물 이름", example = "백두아파트")
    private String structureName;

    @Schema(description = "설명", example = "Near station")
    private String description;
    
}
