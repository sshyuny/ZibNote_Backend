package com.sshyu.zibnote.adapter.in.web.note.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoteFieldReqDto {

    private Long noteFieldId;
    
    private String name;

    private String description;
    
}
