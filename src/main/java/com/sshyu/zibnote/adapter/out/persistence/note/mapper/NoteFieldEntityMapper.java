package com.sshyu.zibnote.adapter.out.persistence.note.mapper;

import com.sshyu.zibnote.adapter.out.persistence.member.mapper.MemberEntityMapper;
import com.sshyu.zibnote.adapter.out.persistence.note.jpa.entity.NoteFieldEntity;
import com.sshyu.zibnote.domain.note.model.NoteField;

public class NoteFieldEntityMapper {

    public static NoteField toDomain(final NoteFieldEntity entity) {
        return NoteField.builder()
                    .noteFieldId(entity.getNoteFieldId())
                    .member(MemberEntityMapper.toDomain(entity.getMemberEntity()))
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .isDeleted(entity.getIsDeleted())
                    .build();
    }
    
    public static NoteFieldEntity toEntity(final NoteField domain) {
        return NoteFieldEntity.builder()
                    .noteFieldId(domain.getNoteFieldId())
                    .memberEntity(MemberEntityMapper.toEntity(domain.getMember()))
                    .name(domain.getName())
                    .description(domain.getDescription())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .isDeleted(domain.getIsDeleted())
                    .build();
    }
}
