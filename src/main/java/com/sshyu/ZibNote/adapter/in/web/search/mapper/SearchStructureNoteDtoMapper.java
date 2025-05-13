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
        return NoteResDto.builder()
                    .searchStructureNoteId(domain.getSearchStructureNoteId())
                    .searchStructureId(domain.getSearchStructure().getSearchStructureId())
                    .noteFieldId(domain.getNoteField().getNoteFieldId())
                    .evalType(domain.getEvalType().getName())
                    .evalValue(domain.getEvalValue())
                    .note(domain.getNote())
                    .build();
    }

}
