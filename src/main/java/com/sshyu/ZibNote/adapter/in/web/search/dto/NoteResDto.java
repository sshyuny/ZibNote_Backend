package com.sshyu.zibnote.adapter.in.web.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class NoteResDto {
    
    private Long searchStructureNoteId;

    private Long searchStructureId;

    private Long noteFieldId;

    private String evalType;

    private String evalValue;

    private String  note;

}
