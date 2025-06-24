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
public class SearchStructureReqDto {
    
    @Schema(description = "임장 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull
    private UUID searchId;

    @Schema(description = "건물 ID", example = "1234")
    @NotNull
    private Long structureId;

    @Schema(description = "설명", example = "Near station")
    @NotBlank
    private String description;

}
