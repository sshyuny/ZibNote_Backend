package com.sshyu.zibnote.adapter.in.web.note.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class NoteFieldResDto {

    private Long noteFieldId;
    
    private String name;

    private String description;

}
