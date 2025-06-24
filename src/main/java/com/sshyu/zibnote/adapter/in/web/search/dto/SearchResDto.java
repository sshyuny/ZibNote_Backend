package com.sshyu.zibnote.adapter.in.web.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchResDto {
    
    @Schema(description = "임장 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String searchId;

    @Schema(description = "제목", example = "산본역 임장")
    private String title;

    @Schema(description = "지역", example = "경기 군포시")
    private String region;

    @Schema(description = "설명", example = "4호선. 개인 임장.")
    private String description;

}
