package com.sshyu.zibnote.adapter.in.web.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NoteFieldReqDto {
    
    @Schema(description = "이름", example = "놀이터 상태")
    @NotBlank
    private String name;

    @Schema(description = "설명", example = "놀이터 전반적인 상태")
    private String description;
    
}
