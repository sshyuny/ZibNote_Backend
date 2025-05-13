package com.sshyu.zibnote.adapter.in.web.note.mapper;

import com.sshyu.zibnote.adapter.in.web.note.dto.NoteFieldResDto;
import com.sshyu.zibnote.domain.note.model.NoteField;

public class NoteFieldDtoMapper {
    
    public static NoteFieldResDto toResDto(NoteField domain) {
        return new NoteFieldResDto(
            domain.getNoteFieldId(), 
            domain.getName(), 
            domain.getDescription()
        );
    }
    
}
