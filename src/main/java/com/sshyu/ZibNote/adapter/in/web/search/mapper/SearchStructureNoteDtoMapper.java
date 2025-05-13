package com.sshyu.zibnote.adapter.in.web.search.mapper;

import com.sshyu.zibnote.adapter.in.web.search.dto.NotePostReqDto;
import com.sshyu.zibnote.adapter.in.web.search.dto.NoteResDto;
import com.sshyu.zibnote.domain.note.model.NoteField;
import com.sshyu.zibnote.domain.search.model.SearchStructure;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

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

    public static NoteResDto toResDto(final SearchStructureNote domain) {
        return new NoteResDto(
            domain.getSearchStructureNoteId(), 
            domain.getSearchStructure().getSearchStructureId(), 
            domain.getNoteField().getNoteFieldId(), 
            domain.getEvalType().getName(), 
            domain.getEvalValue(), 
            domain.getNote()
        );
    }

}
