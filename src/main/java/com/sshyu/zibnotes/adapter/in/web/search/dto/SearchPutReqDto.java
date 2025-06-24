package com.sshyu.zibnotes.adapter.in.web.search.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchPutReqDto {

    @Schema(description = "임장 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull
    private UUID searchId;
    
    @Schema(description = "제목", example = "산본역 임장")
    @NotBlank
    private String title;

    @Schema(description = "지역", example = "경기 군포시")
    @NotBlank
    private String region;

    @Schema(description = "설명", example = "4호선. 개인 임장.")
    private String description;

}
