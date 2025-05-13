package com.sshyu.zibnote.adapter.in.web.note.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NoteFieldResDto {

    private Long noteFieldId;
    
    private String name;

    private String description;

}
