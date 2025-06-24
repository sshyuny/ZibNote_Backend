package com.sshyu.zibnotes.adapter.in.web.note.mapper;

import java.util.UUID;

import com.sshyu.zibnotes.adapter.in.web.note.dto.NoteFieldReqDto;
import com.sshyu.zibnotes.adapter.in.web.note.dto.NoteFieldResDto;
import com.sshyu.zibnotes.domain.member.model.Member;
import com.sshyu.zibnotes.domain.note.model.NoteField;

public class NoteFieldDtoMapper {
    
    public static NoteFieldResDto toResDto(final NoteField domain) {
        return new NoteFieldResDto(
            domain.getNoteFieldId(), 
            domain.getName(), 
            domain.getDescription()
        );
    }

    public static NoteField toDomain(final NoteFieldReqDto reqDto, final UUID membeId) {
        return NoteField.builder()
            .member(Member.onlyId(membeId))
            .name(reqDto.getName())
            .description(reqDto.getDescription())
            .build();
    }
    
}
