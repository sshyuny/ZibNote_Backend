package com.sshyu.zibnote.adapter.in.web.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NoteResDto {
    
    private String searchStructureNoteId;

    private String searchStructureId;

    private Long noteFieldId;

    private String evalType;

    private String evalValue;

    private String  note;

}
