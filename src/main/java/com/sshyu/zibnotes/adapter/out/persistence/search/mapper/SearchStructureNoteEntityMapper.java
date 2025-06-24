package com.sshyu.zibnotes.adapter.out.persistence.search.mapper;

import com.sshyu.zibnotes.adapter.out.persistence.note.mapper.NoteFieldEntityMapper;
import com.sshyu.zibnotes.adapter.out.persistence.search.jpa.entity.SearchStructureNoteEntity;
import com.sshyu.zibnotes.domain.search.model.SearchStructureNote;

public class SearchStructureNoteEntityMapper {
    
    public static SearchStructureNote toDomain(final SearchStructureNoteEntity entity) {
        return SearchStructureNote.builder()
                    .searchStructureNoteId(entity.getSearchStructureNoteId())
                    .searchStructure(SearchStructureEntityMapper.toDomain(entity.getSearchStructureEntity()))
                    .noteField(NoteFieldEntityMapper.toDomain(entity.getNoteFieldEntity()))
                    .evalType(entity.getEvalType())
                    .evalValue(entity.getEvalValue())
                    .note(entity.getNote())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }

    public static SearchStructureNoteEntity toEntity(final SearchStructureNote domain) {
        return SearchStructureNoteEntity.builder()
                    .searchStructureNoteId(domain.getSearchStructureNoteId())
                    .searchStructureEntity(SearchStructureEntityMapper.toEntity(domain.getSearchStructure()))
                    .noteFieldEntity(NoteFieldEntityMapper.toEntity(domain.getNoteField()))
                    .evalType(domain.getEvalType())
                    .evalValue(domain.getEvalValue())
                    .note(domain.getNote())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }

}
