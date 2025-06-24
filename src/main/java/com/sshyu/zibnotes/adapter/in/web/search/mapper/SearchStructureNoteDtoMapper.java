package com.sshyu.zibnotes.adapter.in.web.search.mapper;

import com.sshyu.zibnotes.adapter.in.web.search.dto.NotePostReqDto;
import com.sshyu.zibnotes.adapter.in.web.search.dto.NotePutReqDto;
import com.sshyu.zibnotes.adapter.in.web.search.dto.NoteResDto;
import com.sshyu.zibnotes.domain.note.model.NoteField;
import com.sshyu.zibnotes.domain.search.model.SearchStructure;
import com.sshyu.zibnotes.domain.search.model.SearchStructureNote;

public class SearchStructureNoteDtoMapper {
    
    public static SearchStructureNote toDomain(final NotePostReqDto reqDto) {
        return SearchStructureNote.builder()
                    .searchStructure(SearchStructure.onlyId(reqDto.getSearchStructureId()))
                    .noteField(NoteField.onlyId(reqDto.getNoteFieldId()))
                    .evalType(reqDto.getEvalType())
                    .evalValue(reqDto.getEvalValue())
                    .note(reqDto.getEvalValue())
                    .build();
    }

    public static SearchStructureNote toDomain(final NotePutReqDto reqDto) {
        return SearchStructureNote.builder()
                    .searchStructureNoteId(reqDto.getSearchStructureNoteId())
                    .searchStructure(SearchStructure.onlyId(reqDto.getSearchStructureId()))
                    .noteField(NoteField.onlyId(reqDto.getNoteFieldId()))
                    .evalType(reqDto.getEvalType())
                    .evalValue(reqDto.getEvalValue())
                    .note(reqDto.getEvalValue())
                    .build();
    }

    public static NoteResDto toResDto(final SearchStructureNote domain) {
        return new NoteResDto(
            domain.getSearchStructureNoteId().toString(), 
            domain.getSearchStructure().getSearchStructureId().toString(), 
            domain.getNoteField().getNoteFieldId(), 
            domain.getEvalType().getName(), 
            domain.getEvalValue(), 
            domain.getNote()
        );
    }

}
