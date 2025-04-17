package com.sshyu.zibnote.adapter.out.persistence.search.mapper;

import com.sshyu.zibnote.adapter.out.persistence.note.mapper.NoteFieldMapper;
import com.sshyu.zibnote.adapter.out.persistence.search.jpa.entity.SearchStructureNoteEntity;
import com.sshyu.zibnote.domain.search.model.SearchStructureNote;

public class SearchStructureNoteMapper {
    
    public static SearchStructureNote toDomain(SearchStructureNoteEntity entity) {
        return SearchStructureNote.builder()
                    .searchStructureNoteId(entity.getSearchStructureNoteId())
                    .searchStructure(SearchStructureMapper.toDomain(entity.getSearchStructureEntity()))
                    .noteField(NoteFieldMapper.toDomain(entity.getNoteFieldEntity()))
                    .evalType(entity.getEvalType())
                    .evalValue(entity.getEvalValue())
                    .note(entity.getNote())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }

    public static SearchStructureNoteEntity toEntity(SearchStructureNote domain) {
        return SearchStructureNoteEntity.builder()
                    .searchStructureNoteId(domain.getSearchStructureNoteId())
                    .searchStructureEntity(SearchStructureMapper.toEntity(domain.getSearchStructure()))
                    .noteFieldEntity(NoteFieldMapper.toEntity(domain.getNoteField()))
                    .evalType(domain.getEvalType())
                    .evalValue(domain.getEvalValue())
                    .note(domain.getNote())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }

}
